package ma.ensa.transferservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.transferservice.dto.*;
import ma.ensa.transferservice.mapper.TransferMapper;
import ma.ensa.transferservice.models.User;
import ma.ensa.transferservice.repositories.TsdRepository;
import ma.ensa.transferservice.repositories.TransferRepository;
import ma.ensa.transferservice.services.TransferService;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.time.LocalDateTime.parse;
import static java.time.format.DateTimeFormatter.ofPattern;
import static ma.ensa.transferservice.models.enums.ClientType.*;
import static ma.ensa.transferservice.models.enums.TransferStatusDetail.*;
import static ma.ensa.transferservice.models.enums.TransferType.CASH;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final TsdRepository tsdRepository;
    private final RestCall rest;
    private final TransferChecker checker;

    @Override
    public ma.ensa.transferservice.models.Transfer getTransfer(long ref){
        return transferRepository
                .findById(ref)
                .orElseThrow(
                        // TODO: create a custom exception : TransferNotFound
                        () -> new RuntimeException("transfer does not exists")
                );
    }

    @Override
    public long emitTransfer(TransferRequestDto dto) {

        // from dto to transfer entity
        var transfer = TransferMapper.toEntity(dto);

        // call siron to check the transfer
        rest.callSiron(dto.getSenderRef(), SENDER);

        // debit the amount from wallet or from agent account
        double amount = dto.getAmount();
        if (dto.getTransferType() == CASH) {
            rest.updateAgentBalance(dto.getSentById(), -amount);
        } else {
            amount += transfer.getFeeForSender();
            rest.updateWalletBalance(dto.getSenderRef(), -amount);
        }

        // save the transfer into the database
        transfer = transferRepository.save(transfer);

        // create transfer status
        var tsh = ma.ensa.transferservice.models.TransferStatusDetail.builder()
                .byUser(new User(dto.getSentById()))
                .reason(dto.getReason())
                .transfer(transfer)
                .status(TO_SERVE)
                .build();

        // save the status into the database
        tsdRepository.save(tsh);

        // return the ref
        return transfer.getRef();
    }



    @Override
    public void serveTransfer(PaymentDto dto) {

        // find the transfer
        var transfer = getTransfer(dto.getRef());

        // call SIRON for Recipient
        rest.callSiron(dto.getRef(), RECIPIENT);

        // check serve
        checker.checkServe(transfer);

        // get the amount
        double amountToServe = transfer.getAmountForTheRecipient();

        // call agent service to debit from its account
        rest.updateAgentBalance(dto.getUserId(), -amountToServe);

        // mode of payment --> CASH or WALLET
        if(dto.isToWallet()){
            // call wallet service to update the balance
            var recipientId = transfer.getRecipient().getKycRef().getRef();
            rest.updateWalletBalance(recipientId, amountToServe);
        }

        // save the transfer status into the database
        var tsh = ma.ensa.transferservice.models.TransferStatusDetail.builder()
                .byUser(new User(dto.getUserId()))
                .transfer(transfer)
                .status(SERVED)
                .build();

        tsdRepository.save(tsh);

    }

    @Override
    public void revertTransfer(RevertDto dto) {

        // get the transfer
        var transfer = getTransfer(dto.getRef());

        // check revert
        checker.checkRevert(transfer, dto.getUserId());

        // save the transfer status into the database
        var tsh = ma.ensa.transferservice.models.TransferStatusDetail.builder()
                .byUser(new User(dto.getUserId()))
                .transfer(transfer)
                .status(REVERTED)
                .reason(dto.getReason())
                .build();

        tsdRepository.save(tsh);

    }

    @Override
    public void cancelTransfer(PaymentDto dto) {

    }

    @Override
    public void blockTransfer(PaymentDto dto) {

    }

    @Override
    public void unblockTransfer(PaymentDto dto) {

    }

    @Override
    public List<TransferResponseDto> getAllTransfers(SearchFilter filter) {

        var formatter = ofPattern("dd-MM-yyyy");

        return transferRepository.findAll().stream()
            .filter(t -> {
                if(filter.getIdentity() == null) return true;
                return t.getSender().getIdentity().equals(filter.getIdentity());
            }).filter(t -> {
                if(filter.getGsm() == null) return true;
                return t.getSender().getGsm().equals(filter.getGsm());
            }).filter(t -> {
                if(filter.getStatus() == null) return true;
                return t.getStatusDetails().getStatus() == filter.getStatus();
            }).filter(t -> {
                if(filter.getFromDate() == null) return true;
                var from = parse(filter.getFromDate(), formatter);
                return t.getSentAt().isAfter(from);
            }).filter(t -> {
                if(filter.getToDate() == null) return true;
                var from = parse(filter.getToDate(), formatter);
                return t.getSentAt().isBefore(from);
            })
            .map(TransferMapper::toDto)
            .toList();

    }


}
