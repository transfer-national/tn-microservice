package ma.ensa.pincode.services;


import lombok.RequiredArgsConstructor;
import ma.ensa.pincode.dto.*;
import ma.ensa.pincode.exceptions.*;
import ma.ensa.pincode.models.PinCode;
import ma.ensa.pincode.repositories.PinRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import static java.time.format.DateTimeFormatter.*;
import static ma.ensa.pincode.dto.PinState.*;

@Service
@RequiredArgsConstructor
public class PinCodeServiceImpl implements PinCodeService {

    private final PinRepository pinRepository;

    private final RabbitTemplate rabbitTemplate;

    @Value("${notif-service.exchange}")
    private String exchangeName;

    @Value("${notif-service.routing-key}")
    private String routingKey;

    private final DateTimeFormatter formatter
            = ofPattern("MMM d['st']['nd']['rd']['th'], yyyy HH'h'mm");

    private static final String
    TX_MSG = """
        the PIN code for serving the transfer %s via ATM
        is %s, the transfer is available to serve until %s
    """,
    WD_MSG = """
        the PIN code for validating the debit of %s MAD from wallet %s
        is %s, this PIN code will be expired after 30 minutes
    """;

    private void sendSMS(PinDebit dto){

        final String body =
                String.format(
                        WD_MSG,
                        dto.getAmount(),
                        dto.getWalletId(),
                        dto.getPinCode()
                );

        SMS sms = SMS.builder()
                .body(body)
                .phoneNumber(dto.getPhoneNumber())
                .build();

        // TODO: RabbitMq stuff
        sendSMS(sms);

    }

    private void sendSMS(PinTx dto){

        if(dto.getPhoneNumber() == null) return;

        final String body =
        String.format(
                TX_MSG,
                dto.getTxRef(),
                dto.getPinCode(),
                formatter.format(LocalDateTime.now())
        );

        SMS sms = SMS.builder()
                .body(body)
                .phoneNumber(dto.getPhoneNumber())
                .build();

        // TODO: RabbitMq stuff
        sendSMS(sms);
    }

    @Override
    public void sendSMS(SMS sms){
        rabbitTemplate.convertAndSend(
                exchangeName, routingKey, sms
        );
    }

    @Override
    public List<PinTx> generatePinForTx(List<PinTx> ptx){

        // generate a new pinCode
        return ptx.stream()
            .peek(PinTx::generatePinCode)
            .peek(p -> {
                // from dto to entity
                var pe = PinCode.builder()
                    .pin(p.getPinCode())
                    .relatedId(p.getTxRef())
                    .build();

                pinRepository.save(pe);
            })
            .peek(this::sendSMS)
            .peek(p -> p.setPhoneNumber(null))
            .toList();

    }

    @Override
    public PinState getPinState(String relatedId){

        var pinOpt = pinRepository.find(relatedId);

        if(pinOpt.isEmpty()) return NOT_EXISTS;

        var pin = pinOpt.get();

        if(pin.isExpired()){ return EXPIRED; }

        return pin.isValidated() ? VALID : NOT_VALID;
    }

    @Override
    public String validatePinCode(PinValid dto){

        var relatedId = dto.getWalletId();

        switch (getPinState(relatedId)){

            case VALID ->
                throw new PinAlreadyValidatedException();

            case EXPIRED ->
                throw new PinExpiredException();

            case NOT_EXISTS ->
                throw new NoSuchPinException();
        }

        var pin = pinRepository
            .find(relatedId)
            .orElseThrow(); // it's always not null (checked before)

        if(pin.getPin() != dto.getPin()){
            throw new PinIncorrectException(dto.getPin());
        }

        pinRepository.validatePinCode(pin.getId());

        return "PIN CODE IS VALIDATED";

    }

    @Override
    public String sendPinCodeForDebit(PinDebit pd) {

        var pinCode = pd.generatePinCode();

        var pinEntity = PinCode.builder()
                .relatedId(pd.getWalletId())
                .pin(pinCode)
                .build();

        pinRepository.save(pinEntity);

        sendSMS(pd);

        return "GENERATED SUCCESSFULLY";
    }

}