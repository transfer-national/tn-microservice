package ma.ensa.sironservice.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.sironservice.models.Client;
import ma.ensa.sironservice.models.Transfer;
import ma.ensa.sironservice.models.TransferStatusHistory;
import ma.ensa.sironservice.models.enums.BlockReason;
import ma.ensa.sironservice.models.enums.TransferStatus;
import ma.ensa.sironservice.repositories.TransferRepository;
import ma.ensa.sironservice.repositories.TshRepository;
import ma.ensa.sironservice.services.SironService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static ma.ensa.sironservice.models.enums.BlockReason.*;
import static ma.ensa.sironservice.models.enums.TransferStatus.*;

@Service
@RequiredArgsConstructor
public class SironServiceImpl implements SironService {

    private final TransferRepository transferRepository;

    private final TshRepository tshRepository;

    public Transfer getTransfer(long ref){

        return  transferRepository
                .findById(ref)
                .orElseThrow(
                        // TODO: create a custom exception 'TransferNotFound'
                        () -> new RuntimeException("the transfer not found")
                );
    }

    public void blockTransfer(Transfer transfer, BlockReason br){

        var tsh = TransferStatusHistory.builder()
                .status(BLOCKED)
                .transfer(transfer)
                .reason(br.getReason())
                .build();

        tshRepository.save(tsh);

    }


    @Override
    public boolean isSenderBlackListed(long ref) {

        var transfer = getTransfer(ref);

        boolean isBlackListed = transfer
                .getSender()
                .isBlackListed();

        // TODO: block the transfer if isblackListed
        if(isBlackListed){
            blockTransfer(transfer, SENDER);
        }

        return isBlackListed;
    }

    @Override
    public boolean isRecipientBlackListed(long ref) {

        // get the transfer
        var transfer = getTransfer(ref);

        // check if is blacklisted
        boolean isBlackListed = Optional
                .of(transfer.getRecipient().getKycRef())
                .map(Client::isBlackListed)
                .orElse(false);

        //block the transfer if is blacklisted
        if(isBlackListed){
            blockTransfer(transfer, RECIPIENT);
        }

        return isBlackListed;

    }
}
