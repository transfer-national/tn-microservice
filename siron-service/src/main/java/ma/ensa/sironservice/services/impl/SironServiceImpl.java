package ma.ensa.sironservice.services.impl;

import lombok.RequiredArgsConstructor;
import ma.ensa.sironservice.models.Client;
import ma.ensa.sironservice.models.Transfer;
import ma.ensa.sironservice.repositories.TransferRepository;
import ma.ensa.sironservice.services.SironService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SironServiceImpl implements SironService {

    private final TransferRepository transferRepository;

    public boolean checkBySenderId(long senderId){
        return true;
    }

    public boolean checkBySender(Client client){
        return !client.isBlackListed();
    }

    @Override
    public boolean checkByTransfer(long transferRef){

        // TODO: create a custom exception handler
        Transfer transfer = transferRepository
                .findById(transferRef)
                .orElseThrow(() -> new RuntimeException(" ... "));

        return checkBySender(transfer.getSender());

    }


}
