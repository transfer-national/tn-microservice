package ma.ensa.transferservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.transferservice.dto.ClientDto;
import ma.ensa.transferservice.dto.sms.PinTx;
import ma.ensa.transferservice.dto.RecipientDto;
import ma.ensa.transferservice.dto.SironResponseDto;
import ma.ensa.transferservice.dto.sms.SMS;
import ma.ensa.transferservice.exceptions.BlackListedException;
import ma.ensa.transferservice.models.Client;
import ma.ensa.transferservice.models.Recipient;
import ma.ensa.transferservice.models.Transfer;
import ma.ensa.transferservice.models.enums.ClientType;
import ma.ensa.transferservice.models.enums.TransferStatus;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class RestCall {


    private final RestTemplate restTemplate;
    private final RabbitTemplate rabbitTemplate;

    public void callSiron(long ref, ClientType type){

        var t2s = type.name().toLowerCase();

        SironResponseDto result = null;

        try {
            // 3 instance siron-service 51456, 52639, 53569
            // lb ? load balancer -----> 52639 (round-robin algorithm)
            result = restTemplate.getForObject(
                "lb://siron-service/{t2s}/{ref}",
                SironResponseDto.class, t2s, ref
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

        // restTemplate.exchange("", HttpMethod.PUT, null, Object.class);


    }


    public List<PinTx> generatePinCode(List<PinTx> ptx){

        var result = restTemplate.postForObject(
            "lb://pin-code-service/tx",
            ptx, PinTx[].class
        );

        return Optional.ofNullable(result)
                .map(List::of)
                .orElseThrow();

    }


    public ClientDto getSender(Client sender) {
        return restTemplate.getForObject(
            "lb://client-service/client/{ref}",
            ClientDto.class, sender.getRef()
        );
    }

    public RecipientDto getRecipient(Recipient recipient) {

        return restTemplate.getForObject(
                "lb://client-service/recipient/{id}",
                RecipientDto.class, recipient.getId()
        );

    }

    public void sendStatusViaSMS(Transfer tx, TransferStatus status) {

        if(!tx.isNotificationEnabled()) return;

        var message = String.format(
                "the transfer %d is now %s",
                tx.getRef(),
                status.name()
                    .replace("_", " ")
                    .toLowerCase()
        );

        var sms = SMS.builder()
                .phoneNumber(tx.getSender().getGsm())
                .body(message)
                .build();

        restTemplate.postForObject(
                "lb://pin-code-service/sms",
                sms, Void.class
        );


    }
}
