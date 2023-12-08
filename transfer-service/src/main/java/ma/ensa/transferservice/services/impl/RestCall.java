package ma.ensa.transferservice.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.transferservice.dto.ClientDto;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.enums.ClientType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class RestCall {


    private final RestTemplate restTemplate;



    public void callSiron(long ref, ClientType type){

        var str = type.name().toLowerCase();

        final String path =
            String.format("lb://siron-service/%s/%d",str, ref);

        Boolean bl = null;

        try {
            bl = restTemplate.getForObject(
                    path, Boolean.class
            );
        }catch (Exception ex){
            log.warn("siron service is DOWN !!");
        }


        if(bl != null && bl){
            String em = String.format(
                    "the %s is black listed",
                    type.toString().toLowerCase()
            );
            throw new RuntimeException(em);
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
