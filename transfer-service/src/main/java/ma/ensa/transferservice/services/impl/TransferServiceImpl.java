package ma.ensa.transferservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.transferservice.config.TransferConfig;
import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.exceptions.TransferNotFound;
import ma.ensa.transferservice.mapper.TransferMapper;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.TransferStatusDetails;
import ma.ensa.transferservice.models.users.User;
import ma.ensa.transferservice.repositories.TsdRepository;
import ma.ensa.transferservice.repositories.TransferRepository;
import ma.ensa.transferservice.services.RestCall;
import ma.ensa.transferservice.services.TransferChecker;
import ma.ensa.transferservice.services.TransferService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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

    private void saveStatus(Transfer transfer, @NotNull TransferDto dto){

        var status = switch(dto.getActionType()) {
            case EMIT    -> TO_SERVE;
            case SERVE   -> SERVED;
            case REVERT  -> REVERTED;
            case CANCEL  -> CANCELLED;
            case BLOCK   -> BLOCKED;
            case UNBLOCK -> UNBLOCKED_TO_SERVE;
        };

        var statusDetails = TransferStatusDetails.builder()
                .byUser(new User(dto.getUserId()))
                .reason(dto.getReason())
                .transfer(transfer)
                .status(status)
                .build();

        tsdRepository.save(statusDetails);

    }

    private Transfer getTransferEntity(long ref){
        return transferRepository
            .findById(ref)
            .orElseThrow(TransferNotFound::new);
    }

    @Override
    public TransferResponseDto getTransfer(long ref){
        return mapper.toDto(
            getTransferEntity(ref)
        );
    }

    @Override
    public List<Long> emitTransfer(SendDto dto) {

        // from dto to transfer entity
        var transfers = mapper.toEntity(dto);

        // call siron to check the transfer
        rest.callSiron(dto.getSenderRef(), SENDER);

        // debit the amount from wallet or from agent account

        double amount = transfers
                .stream()
                .mapToDouble(Transfer::getAmount)
                .sum();

        double fees = transfers.stream()
                .map(Transfer::getFeeType)
                .mapToDouble(config::getFeeForSender)
                .sum();

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
    public String serveTransfer(@NotNull TransferDto dto) {

        // find the transfer
        var transfer = getTransferEntity(dto.getRef());

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
        saveStatus(transfer, dto);

        return "SERVED SUCCESSFULLY";

    }

    @Override
    public String revertTransfer(@NotNull TransferDto dto) {

        var transfer = getTransferEntity(dto.getRef());

        // check revert
        checker.checkRevert(transfer, dto.getUserId());

        // save the new status into the database
        saveStatus(transfer, dto);

        return "REVERTED SUCCESSFULLY";

    }

    @Override
    public CancelResponseDto cancelTransfer(@NotNull TransferDto dto) {

        final int count;
        final int cancelledCount;
        final List<Long> cancelledTransfers;

        // get the transfer
        var transfer = getTransferEntity(dto.getRef());

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
    public String blockTransfer(@NotNull TransferDto dto) {

        // get the transfer entity
        var transfer = getTransferEntity(dto.getRef());

        // TODO: check the condition

        // save the new status into the database
        saveStatus(transfer, dto);

        return "BLOCKED SUCCESSFULLY";

    }

    @Override
    public String unblockTransfer(@NotNull TransferDto dto) {

        // get the transfer entity
        var transfer = getTransferEntity(dto.getRef());

        // TODO: check the condition

        // save the new status into the database
        saveStatus(transfer, dto);

        return "UNBLOCKED SUCCESSFULLY";
    }

    @Override
    public List<TransferResponseDto> getAllTransfers(@NotNull SearchFilter f) {

        var formatter = ofPattern("dd-MM-yyyy");

        var stream = transferRepository.findAll().stream();

        // filter by idNumber
        if(f.getIdentity() != null){
            stream = stream.filter(t ->
                t.getSender().getIdentity().equals(f.getIdentity())
            );
        }

        // filter by gsm
        if(f.getGsm() != null){
            stream = stream.filter(t ->
                t.getSender().getGsm().equals(f.getGsm())
            );
        }

        // filter by status
        if(f.getStatus() != null){
            stream = stream.filter(t ->
                t.getStatusDetails().getStatus() == f.getStatus()
            );
        }

        // filter by fromDate
        if(f.getFromDate() != null){
            var from = parse(f.getFromDate(), formatter);
            stream = stream.filter(t ->
                t.getSentAt().isAfter(from)
            );
        }

        // filter by toDate
        if(f.getToDate() != null){
            var to = parse(f.getToDate(), formatter);
            stream = stream.filter(t ->
                t.getSentAt().isBefore(to)
            );
        }

        // map from transfer to transferDto
        return stream.map(mapper::toDto).toList();

    }

    @Override
    public TransferResume getTransferResume(long senderRef, int lastDays) {
        var last = LocalDateTime.now().minusDays(lastDays);
        return transferRepository.getTransferResume(senderRef, last);
    }

}