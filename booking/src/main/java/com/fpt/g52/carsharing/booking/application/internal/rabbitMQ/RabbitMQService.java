package com.fpt.g52.carsharing.booking.application.internal.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fpt.g52.common_service.notification.model.shared.constant.NotificationSharedConstant;

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
    
    @Value("${rabbitmq.rate-review-complete.exchange}")
    String reviewCmptExchange;

    @Value("${rabbitmq.rate-review-complete.routingkey}")
    private String reviewCmptRoutingkey;
    
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendtoPayment(T t) {
        rabbitTemplate.convertAndSend(paymentExchange, paymentRoutingkey, t);
    }
    
    public void sendtoReview(T t) {
        rabbitTemplate.convertAndSend(reviewExchange, reviewRoutingkey, t);
    }
    
    public void sendtoReviewCmpt(T t) {
        rabbitTemplate.convertAndSend(reviewCmptExchange, reviewCmptRoutingkey, t);
    }
    
    public void sendtoNotice(T t) {
        rabbitTemplate.convertAndSend(NotificationSharedConstant.NOTIFY_TOPIC_EXCHANGE, NotificationSharedConstant.NOTIFY_ORDER_PAYMENT_ROUTING_KEY, t);
    }
}
