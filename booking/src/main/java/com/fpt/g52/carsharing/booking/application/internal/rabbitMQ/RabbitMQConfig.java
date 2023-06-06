package com.fpt.g52.carsharing.booking.application.internal.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fpt.g52.common_service.notification.model.shared.constant.NotificationSharedConstant;

@Configuration
public class RabbitMQConfig {
    
    @Value("${rabbitmq.payment.queue}")
    String paymentQueue;

    @Value("${rabbitmq.payment.exchange}")
    String paymentExchange;

    @Value("${rabbitmq.payment.routingkey}")
    private String paymentRoutingkey;

    @Value("${rabbitmq.rate-review.queue}")
    String reviewQueue;

    @Value("${rabbitmq.rate-review.exchange}")
    String reviewExchange;

    @Value("${rabbitmq.rate-review.routingkey}")
    private String reviewRoutingkey;
    
    @Value("${rabbitmq.rate-review-complete.queue}")
    String reviewCmptQueue;

    @Value("${rabbitmq.rate-review-complete.exchange}")
    String reviewCmptExchange;

    @Value("${rabbitmq.rate-review-complete.routingkey}")
    private String reviewCmptRoutingkey;
    
    @Value("${rabbitmq.notice.queue}")
    String noticeQueue;

    @Value("${rabbitmq.notice.exchange}")
    String noticeExchange;

    @Value("${rabbitmq.notice.routingkey}")
    private String noticeRoutingkey;
    
    @Bean
    Queue queuePayment() {
        return QueueBuilder.durable(paymentQueue).withArgument("x-dead-letter-exchange", "payment-dlq-exchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }
    
    @Bean
    Queue reviewPayment() {
        return QueueBuilder.durable(reviewQueue).build();
    }
    
    @Bean
    Queue reviewCmptPayment() {
        return QueueBuilder.durable(reviewCmptQueue).build();
    }
    
    @Bean
    Queue queueNotice() {
    	return QueueBuilder.durable(noticeQueue).withArgument("x-dead-letter-exchange", "notify.dl.topic.exchange")
                .withArgument("x-dead-letter-routing-key", "notify.order.payment.routing.key").build();
    }

    @Bean
    DirectExchange paymentExchange() {
        return new DirectExchange(paymentExchange);
    }
    
    @Bean
    DirectExchange reviewExchange() {
        return new DirectExchange(reviewExchange);
    }
    
    @Bean
    DirectExchange reviewCmptExchange() {
        return new DirectExchange(reviewCmptExchange);
    }
    
    @Bean
    TopicExchange noticeExchange() {
        return new TopicExchange(NotificationSharedConstant.NOTIFY_TOPIC_EXCHANGE);
    }

    
    @Bean
    Queue dlqPayment() {
        return QueueBuilder.durable("Payment_DLQ_DEMO").build();
    }

    @Bean
    DirectExchange deadLetterPaymentExchange() {
        return new DirectExchange("payment-dlq-exchange");
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlqPayment()).to(deadLetterPaymentExchange()).with("deadLetter");
    }
    
    @Bean
    Queue dlqNotice() {
        return QueueBuilder.durable("notify.order.payment.dl.topic.queue").build();
    }
    
    @Bean
    TopicExchange deadLetterNoticeExchange() {
        return new TopicExchange("notify.dl.topic.exchange");
    }

    @Bean
    Binding noticeDLQBinding() {
        return BindingBuilder.bind(dlqNotice()).to(deadLetterNoticeExchange()).with("notify.order.payment.routing.key");
    }

    @Bean
    Binding paymentBinding() {
        return BindingBuilder.bind(queuePayment()).to(paymentExchange()).with(paymentRoutingkey);
    }
    
    @Bean
    Binding reviewBinding() {
        return BindingBuilder.bind(reviewPayment()).to(reviewExchange()).with(reviewRoutingkey);
    }
    
    @Bean
    Binding reviewCmptBinding() {
        return BindingBuilder.bind(reviewCmptPayment()).to(reviewCmptExchange()).with(reviewCmptRoutingkey);
    }

    @Bean
    Binding noticeBinding() {
        return BindingBuilder.bind(queueNotice()).to(noticeExchange()).with(NotificationSharedConstant.NOTIFY_ORDER_PAYMENT_ROUTING_KEY);
    }
    
    @Bean
    MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    AmqpTemplate rabbitTemplateA(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
