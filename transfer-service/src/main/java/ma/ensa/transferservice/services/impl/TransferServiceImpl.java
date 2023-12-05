package ma.ensa.transferservice.services.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import ma.ensa.transferservice.dto.TransferDto;
import ma.ensa.transferservice.mapper.TransferMapper;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.TransferStatusHistory;
import ma.ensa.transferservice.models.User;
import ma.ensa.transferservice.models.enums.TransferStatus;
import ma.ensa.transferservice.models.enums.TransferType;
import ma.ensa.transferservice.repositories.TSHRepository;
import ma.ensa.transferservice.repositories.TransferRepository;
import ma.ensa.transferservice.services.TransferService;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static ma.ensa.transferservice.models.enums.TransferStatus.*;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final TSHRepository tshRepository;
    private final LoadBalancerClient loadBalancerClient;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init(){
        restTemplate = new RestTemplate();
    }

    private String getServiceUrl(String serviceId){
        return loadBalancerClient
                .choose(serviceId+"-service")
                .getUri()
                .toString();
    }

    private void callSironForSender(long ref){
        final String path = String.format(
                "%s/sender/%d",
                getServiceUrl("siron"), ref
        );

        Boolean bl = restTemplate.getForObject(
                path, Boolean.class
        );

        if(bl != null && !bl){
            throw new RuntimeException("the sender is black listed");
        }
    }

    @Override
    public long emitTransfer(TransferDto dto) {

        // call siron
        callSironForSender(dto.getSenderRef());

        // if the transferType is DEBIT OR WALLET
        if(dto.getTransferType() != TransferType.CASH ){
            // TODO: debit from the account(REST CALL)
            // and have to be completed successfully
            // otherwise complete
        }

        // dto ---> transfer
        Transfer transfer = TransferMapper.map(dto);

        // persist the instance into the database
        transfer = transferRepository.save(transfer);

        // create transfer status
        var tsh = TransferStatusHistory.builder()
                .byUser(new User(dto.getSentById()))
                .reason(dto.getReason())
                .transfer(transfer)
                .status(TO_SERVE)
                .build();

        // save the status into the database
        tshRepository.save(tsh);

        // return the ref
        return transfer.getRef();

    }

    @Override
    public TransferStatus getTransferStatus(long ref) {
        return tshRepository
                .findTransferStatusByRef(ref);
    }

    @Override
    public void serveTransfer(long ref) {

        // find the transfer
        Transfer transfer = transferRepository
                .findById(ref)
                .orElseThrow(
                        // TODO: create a custom exception : TransferNotFound
                        () -> new RuntimeException("transfer does not exists")
                );

        // get the transfer status
        TransferStatus status = getTransferStatus(ref);

        if(status != TO_SERVE && status != UNBLOCKED_TO_SERVE){
            // TODO: create a custom exception : TransferCannotBeServed
            throw new RuntimeException("it's not able to serve");
        }

        // get the amount
        double amountToServe = transfer.getAmountForTheRecipient();

    }

    @Override
    public void revertTransfer(long ref) {

    }

    @Override
    public void cancelTransfer(long ref) {

    }

    @Override
    public void blockTransfer(long ref) {

    }

    @Override
    public void unblockTransfer() {

    }
}
