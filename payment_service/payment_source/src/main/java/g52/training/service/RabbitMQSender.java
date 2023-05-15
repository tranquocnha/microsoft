package g52.training.service;


import g52.training.dto.Employee;
import g52.training.event.PaymentBookingEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplateA;
//
//    @Value("${microservice_training.rabbitmq.exchange}")
//    private String exchange;
//
//    @Value("${microservice_training.rabbitmq.routingkey}")
//    private String routingkey;
//
//    public void send(Employee company) {
//        rabbitTemplateA.convertAndSend(exchange, routingkey, company);
//        System.out.println("Send msg = " + company);
//
//    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Autowired
//    private Queue queue;

    /*
     * public RabbitMqSender(RabbitTemplate rabbitTemplate) { this.rabbitTemplate =
     * rabbitTemplate; }
     */

    public void send(PaymentBookingEvent paymentBookingEvent){
        rabbitTemplateA.convertAndSend("MQ_DEMO", paymentBookingEvent);
    }
}
