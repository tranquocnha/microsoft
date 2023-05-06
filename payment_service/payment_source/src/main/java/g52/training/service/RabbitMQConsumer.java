package g52.training.service;

import g52.training.dto.Employee;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
    @RabbitListener(queues = "${microservice_training.rabbitmq.queue}")
    public void recievedMessage(Employee employee) {
        System.out.println("Recieved Message From RabbitMQ: " + employee);
    }
}
