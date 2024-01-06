package ma.ensa.pincode.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    @Value("${notif-service.queue}")
    private String queueName;

    @Value("${notif-service.exchange}")
    private String exchangeName;

    @Value("${notif-service.routing-key}")
    private String routingKey;

    @Bean
    public Queue queue(){
        return new Queue(queueName);
    }

    @Bean
    public Exchange exchange(){
        return new DirectExchange(exchangeName);
    }

    @Bean
    public Binding binding(Queue queue, Exchange exchange){
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey)
                .noargs();
    }


    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate(
            ConnectionFactory factory,
            MessageConverter converter
    ){
        return new RabbitTemplate(factory){{
            setMessageConverter(converter);
        }};
    }


}