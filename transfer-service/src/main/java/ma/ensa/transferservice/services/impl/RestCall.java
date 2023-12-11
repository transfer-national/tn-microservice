package ma.ensa.transferservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.transferservice.dto.ClientDto;
import ma.ensa.transferservice.dto.SironResponseDto;
import ma.ensa.transferservice.exceptions.BlackListedException;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.enums.ClientType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class RestCall {


    private final RestTemplate restTemplate;

    public void callSiron(long ref, ClientType type){

        var t2s = type.name().toLowerCase();

        SironResponseDto result = null;

        try {
            result = restTemplate.getForObject(
                String.format("lb://siron-service/%s/%d",t2s, ref),
                SironResponseDto.class
            );
        }catch (Exception ex){
            log.warn("siron service is DOWN !!");
        }

        if(result != null){
            throw new BlackListedException(result.getReason());
        }

    }

    public void updateAgentBalance(String agentId, double amount){


    }

    public void updateWalletBalance(long clientRef, double amount){


    }

    public void sendNotification(){

    }


    public void debitAccount() {

    }

    public ClientDto getSender(Client sender) {
        return restTemplate.getForObject(
            "lb://client-service/client/{ref}",
            ClientDto.class, sender.getRef()
        );
    }

    public ClientDto getRecipient(Recipient recipient) {

        return Optional.of(recipient.getKycRef())
            .map(this::getSender)
            .orElse(
                restTemplate.getForObject(
                    "lb://client-service/recipient/{id}",
                    ClientDto.class, recipient.getId()
                )
            );

    }
}
