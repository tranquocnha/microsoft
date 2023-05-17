package g52.training.service;


import g52.training.event.publisher.MessageSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender<T> implements MessageSender<T> {

    @Autowired
    private AmqpTemplate rabbitTemplateA;

    @Value("${microservice_training.rabbitmq.exchange}")
    private String exchange;

    @Value("${microservice_training.rabbitmq.routingkey}")
    private String routingkey;

    @Override
    public void send(T t) {
        rabbitTemplateA.convertAndSend(exchange, routingkey, t);
    }
}
