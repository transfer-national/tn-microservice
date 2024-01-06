package ma.ensa.emailservice;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public JavaMailSender javaMailSender() {

        var props = new Properties(){{
            put("mail.transport.protocol", "smtp");
            put("mail.smtp.auth", "true");
            put("mail.smtp.starttls.enable", "true");
            put("mail.debug", "true");
        }};

        return new JavaMailSenderImpl(){{
           setHost("smtp.gmail.com");
           setPort(587);
           setUsername("your@gmail.com");
           setPassword("jslv ivcg xtnu pnpm");
           setJavaMailProperties(props);
        }};
    }


    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory factory){
        return new RabbitTemplate(factory){{
            setMessageConverter(messageConverter());
        }};
    }

}