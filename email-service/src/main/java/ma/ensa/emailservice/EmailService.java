package ma.ensa.emailservice;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender sender;

    @RabbitListener(queues = "q.mail")
    public void sendMail(EmailDto dto){

        var mail = new SimpleMailMessage(){{
           setTo(dto.getTo());
           setSubject(dto.getSubject());
           setFrom("mohcine.sahtani@gmail.com");
           setText(dto.getBody());
        }};

        sender.send(mail);

    }

}
