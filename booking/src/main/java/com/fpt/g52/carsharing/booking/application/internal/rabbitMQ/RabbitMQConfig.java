package com.fpt.g52.carsharing.booking.application.internal.rabbitMQ;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate rabbitTemplateA(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
