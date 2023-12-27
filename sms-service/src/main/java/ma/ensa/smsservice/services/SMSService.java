package ma.ensa.smsservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.ensa.smsservice.dto.SMS;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class SMSService {

    private final SseService service;

    @RabbitListener(queues = {"q.sms"})
    public void send(SMS sms){

        log.info("sms received : {}", sms);
        service.send(sms);

    }


}
