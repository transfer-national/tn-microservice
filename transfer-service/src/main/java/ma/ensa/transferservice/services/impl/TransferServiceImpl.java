package ma.ensa.transferservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.transferservice.config.TransferConfig;
import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.dto.request.*;
import ma.ensa.transferservice.mapper.TransferMapper;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.TransferStatusDetails;
import ma.ensa.transferservice.models.User;
import ma.ensa.transferservice.models.enums.TransferStatus;
import ma.ensa.transferservice.repositories.TsdRepository;
import ma.ensa.transferservice.repositories.TransferRepository;
import ma.ensa.transferservice.services.TransferService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static ma.ensa.transferservice.models.enums.ClientType.*;
import static ma.ensa.transferservice.models.enums.TransferStatus.*;
import static ma.ensa.transferservice.models.enums.TransferType.CASH;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final TsdRepository tsdRepository;

    private final RestCall rest;
    private final TransferChecker checker;
    private final TransferConfig config;
    private final TransferMapper mapper;


    private void saveStatus(Transfer transfer, TransferDto dto){

        Map<Class<? extends TransferDto>, TransferStatus> t2s =
            Map.of(
                SendDto.class,    TO_SERVE,
                ServeDto.class,   SERVED,
                RevertDto.class,  REVERTED,
                CancelDto.class,  CANCELLED,
                BlockDto.class,   BLOCKED,
                UnblockDto.class, UNBLOCKED_TO_SERVE
            );

        var statusDetails = TransferStatusDetails.builder()
                .byUser(new User(dto.getUserId()))
                .reason(dto.getReason())
                .transfer(transfer)
                .status(t2s.get(dto.getClass()))
                .build();

        tsdRepository.save(statusDetails);

    }

    @Override
    public Transfer getTransfer(long ref){
        return transferRepository
            .findById(ref)
            .orElseThrow(
                // TODO: create a custom exception : TransferNotFound
                () -> new RuntimeException("transfer does not exists")
            );
    }

    @Override
    public List<Long> emitTransfer(SendDto dto) {

        // from dto to transfer entity
        var transfers = mapper.toEntity(dto);

        // call siron to check the transfer
        rest.callSiron(dto.getSenderRef(), SENDER);

        // debit the amount from wallet or from agent account
        int recipientCount = dto.getRecipientCount();
        double amount = dto.getAmount() * recipientCount;
        double fees = config.getFeeForSender(dto.getFeeType()) * recipientCount;

        if (dto.getTransferType() == CASH) {
            rest.updateAgentBalance(dto.getUserId(), -amount);
        } else {
            // TODO: change this line of code later
            amount += fees;
            rest.updateWalletBalance(dto.getSenderRef(), -amount);
        }

        // return the refs
        return transferRepository
            .saveAll(transfers)
            .stream()
            .peek(t -> saveStatus(t, dto))
            .map(Transfer::getRef)
            .toList();
    }

    @Override
    public void serveTransfer(ServeDto dto) {

        // find the transfer
        var transfer = getTransfer(dto.getRef());

        // call SIRON for Recipient
        rest.callSiron(dto.getRef(), RECIPIENT);

        // check serve
        checker.checkServe(transfer);

        // get the amount
        double fee = config.getFeeForRecipient(transfer.getFeeType());
        double amountToServe = transfer.getAmount() - fee;

        // call agent service to debit from its account
        rest.updateAgentBalance(dto.getUserId(), -amountToServe);

        // mode of payment --> CASH or WALLET
        if(dto.isToWallet()){
            // call wallet service to update the balance
            var recipientId = transfer.getRecipient().getKycRef().getRef();
            rest.updateWalletBalance(recipientId, amountToServe);
        }

        // save the transfer status into the database
        var tsh = TransferStatusDetails.builder()
                .byUser(new User(dto.getUserId()))
                .transfer(transfer)
                .status(SERVED)
                .build();

        tsdRepository.save(tsh);

    }

    @Override
    public RevertResponseDto revertTransfer(RevertDto dto) {

        final int count;
        final int revertedCount;
        final List<Long> revertedTransfers;

        // get the transfer
        var transfer = getTransfer(dto.getRef());

        // check revert
        checker.checkRevert(transfer, dto.getUserId());

        if(transfer.isMultiple()){

            var transfers = transferRepository.getAllByGroupId(transfer.getGroupId());

            revertedTransfers = transfers
                .stream()
                .filter(t-> {
                    var status = t.getStatusDetails().getStatus();
                    return status == TO_SERVE || status == UNBLOCKED_TO_SERVE;
                }).peek(t-> saveStatus(t, dto))
                .map(Transfer::getRef)
                .toList();

            count = transfers.size();
            revertedCount = revertedTransfers.size();
        }else{
            count = 1;
            var status = transfer.getStatusDetails().getStatus();
            if(status == TO_SERVE || status == UNBLOCKED_TO_SERVE){

                saveStatus(transfer, dto);

                revertedTransfers = List.of(transfer.getRef());
                revertedCount = 1;
            }else{
                revertedTransfers = Collections.emptyList();
                revertedCount = 0;
            }
        }

        return RevertResponseDto.builder()
                .count(count)
                .revertedCount(revertedCount)
                .refs(revertedTransfers)
                .build();

    }

    @Override
    public CancelResponseDto cancelTransfer(CancelDto dto) {

        final int count;
        final int cancelledCount;
        final List<Long> cancelledTransfers;

        // get the transfer
        var transfer = getTransfer(dto.getRef());

        // check revert
        checker.checkRevert(transfer, dto.getUserId());

        if(transfer.isMultiple()){

            var transfers = transferRepository.getAllByGroupId(transfer.getGroupId());

            cancelledTransfers = transfers
                    .stream()
                    .filter(t-> {
                        var status = t.getStatusDetails().getStatus();
                        return status == TO_SERVE || status == UNBLOCKED_TO_SERVE;
                    }).peek(t-> saveStatus(t, dto))
                    .map(Transfer::getRef)
                    .toList();

            count = transfers.size();
            cancelledCount = cancelledTransfers.size();
        }else{
            count = 1;
            var status = transfer.getStatusDetails().getStatus();
            if(status == TO_SERVE || status == UNBLOCKED_TO_SERVE){
                saveStatus(transfer, dto);
                cancelledTransfers = List.of(transfer.getRef());
                cancelledCount = 1;
            }else{
                cancelledTransfers = Collections.emptyList();
                cancelledCount = 0;
            }
        }

        return CancelResponseDto.builder()
                .count(count)
                .cancelledCount(cancelledCount)
                .refs(cancelledTransfers)
                .build();
    }

    @Override
    public void blockTransfer(ServeDto dto) {

    }

    @Override
    public void unblockTransfer(ServeDto dto) {

    }

    @Override
    public List<TransferResponseDto> getAllTransfers(SearchFilter f) {

        var formatter = ofPattern("dd-MM-yyyy");

        return transferRepository.findAll().stream()
            .filter(t -> {
                if(f.getIdentity() == null) return true;
                return t.getSender().getIdentity().equals(f.getIdentity());
            }).filter(t -> {
                if(f.getGsm() == null) return true;
                return t.getSender().getGsm().equals(f.getGsm());
            }).filter(t -> {
                if(f.getStatus() == null) return true;
                return t.getStatusDetails().getStatus() == f.getStatus();
            }).filter(t -> {
                if(f.getFromDate() == null) return true;
                var from = parse(f.getFromDate(), formatter);
                return t.getSentAt().isAfter(from);
            }).filter(t -> {
                if(f.getToDate() == null) return true;
                var to = parse(f.getToDate(), formatter);
                return t.getSentAt().isBefore(to);
            })
            .map(mapper::toDto)
            .toList();

    }
}
