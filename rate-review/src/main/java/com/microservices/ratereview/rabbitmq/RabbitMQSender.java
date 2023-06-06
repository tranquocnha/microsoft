package com.microservices.ratereview.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class RabbitMQSender {

    @Value("${rabbitmq.exchange.dlq.name}")
    private String exchange;

    @Value("${ratereview.dlq.key}")
    private String routingJsonKey;

    @Value("${rabbitmq.exchange.name}")
    private String exchange1;

    @Value("${ratereview.complete.key}")
    private String routingKey;
    
    @Value("${ratereview.start.key}")
    private String startroutingKey;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);

    private RabbitTemplate rabbitTemplate;

    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJsonMessage(RabbitDTO user){
        LOGGER.info(String.format("Json message sent -> %s", user.toString()));
        rabbitTemplate.convertAndSend(exchange1, startroutingKey, user);
    }
    public void sendMessage(Object str){
        LOGGER.info(String.format("Message sent -> %s", str));
        rabbitTemplate.convertAndSend(exchange, routingJsonKey, str);
    }
    public void sendJsonMessage1(RabbitDTO str){
        LOGGER.info(String.format("Message sent -> %s", str));
        rabbitTemplate.convertAndSend(exchange1, routingKey, str);
    }
}