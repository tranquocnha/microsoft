package g52.training.service;


import g52.training.dto.Employee;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplateA;

    @Value("${microservice_training.rabbitmq.exchange}")
    private String exchange;

    @Value("${microservice_training.rabbitmq.routingkey}")
    private String routingkey;

    public void send(Employee company) {
        rabbitTemplateA.convertAndSend(exchange, routingkey, company);
        System.out.println("Send msg = " + company);

    }
}
