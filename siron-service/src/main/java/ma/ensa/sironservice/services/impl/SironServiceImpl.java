package ma.ensa.sironservice.services.impl;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import ma.ensa.sironservice.models.*;
import ma.ensa.sironservice.models.enums.BlockReason;
import ma.ensa.sironservice.repositories.BlcRepository;
import ma.ensa.sironservice.repositories.ClientRepository;
import ma.ensa.sironservice.repositories.TransferRepository;
import ma.ensa.sironservice.repositories.TshRepository;
import ma.ensa.sironservice.services.SironService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static ma.ensa.sironservice.models.enums.BlockReason.*;
import static ma.ensa.sironservice.models.enums.TransferStatus.*;

@Service
@RequiredArgsConstructor
public class SironServiceImpl implements SironService {

    private final TransferRepository transferRepository;
    private final ClientRepository clientRepository;
    private final TshRepository tshRepository;
    private final BlcRepository blcRepository;

    private Transfer tx;

    private Transfer store(Transfer t){
        return (tx = t);
    }

    private void blockTransfer(BlockReason br){

        var tsh = TransferStatusHistory.builder()
                .status(BLOCKED)
                .transfer(tx)
                .reason(br.getReason())
                .build();

        tshRepository.save(tsh);
    }

    private Optional<BlackListedClient> getBlackListed(Client client){
        return blcRepository
            .findBlackListedClientByClient(client);
    }

    private Optional<Transfer> getTransfer(long ref){
        return transferRepository.findById(ref);
    }

    @Override
    public BlackListedClient isSenderBlackListed(long ref) {

        return clientRepository
            .findById(ref)
            .flatMap(this::getBlackListed)
            .orElse(null);

    }

    @Override
    public BlackListedClient isSenderBlackListedByTransfer(long txRef) {

        return getTransfer(txRef)
            .map(this::store)
            .map(Transfer::getSender)
            .flatMap(this::getBlackListed)
            .map(b -> {
                blockTransfer(SENDER);
                return b;
            })
            .orElse(null);
    }

    @Override
    public BlackListedClient isRecipientBlackListed(long txRef) {

        return getTransfer(txRef)
            .map(this::store)
            .map(Transfer::getRecipient)
            .map(Recipient::getKycRef)
            .flatMap(this::getBlackListed)
            .map(b -> {
                blockTransfer(RECIPIENT);
                return b;
            })
            .orElse(null);
    }

}