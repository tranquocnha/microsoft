package com.fpt.g52.carsharing.booking.application.internal.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService<T> {

    @Value("${rabbitmq.payment.exchange}")
    String paymentExchange;

    @Value("${rabbitmq.payment.routingkey}")
    private String paymentRoutingkey;
    
    @Value("${rabbitmq.rate-review.exchange}")
    String reviewExchange;

    @Value("${rabbitmq.rate-review.routingkey}")
    private String reviewRoutingkey;
    
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendtoPayment(T t) {
        rabbitTemplate.convertAndSend(paymentExchange, paymentRoutingkey, t);
    }
    
    public void sendtoReview(T t) {
        rabbitTemplate.convertAndSend(reviewExchange, reviewRoutingkey, t);
    }
}
