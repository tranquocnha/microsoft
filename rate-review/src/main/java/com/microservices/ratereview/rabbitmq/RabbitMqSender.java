package com.microservices.ratereview.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    /*
     * public RabbitMqSender(RabbitTemplate rabbitTemplate) { this.rabbitTemplate =
     * rabbitTemplate; }
     */

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(this.queue.getName(), message);
    }
}
