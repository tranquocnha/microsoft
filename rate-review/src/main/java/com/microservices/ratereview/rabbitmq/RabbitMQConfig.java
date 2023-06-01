package com.microservices.ratereview.rabbitmq;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
    @Value("${rabbitmq.exchange.dlq.name}")
    private String exchangedlq;
    @Value("${rabbitmq.queue.dlq.name}")
    private String dlQueue;
    @Value("${ratereview.dlq.key}")
    private String ratereviewDql;
    
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    
    @Value("${rabbitmq.queue.json.name}")
    private String jsonQueue;
    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${ratereview.complete.key}")
    private String ratereviewComplete;
    @Value("${ratereview.start.key}")
    private String ratereviewStart;
    
    // spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }
    @Bean
    public Queue dlqQueue(){
        return new Queue(dlQueue);
    }
    // spring bean for queue (store json messages)
    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueue);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }
    // spring bean for rabbitmq exchange
    @Bean
    public TopicExchange exchangedlq(){
        return new TopicExchange(exchangedlq);
    }
    // binding between queue and exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ratereviewComplete);
    }
    // binding between json queue and exchange using routing key
    @Bean
    public Binding jsonBinding(){
        return BindingBuilder
                .bind(jsonQueue())
                .to(exchange())
                .with(ratereviewStart);
    }
    @Bean
    public Binding dlqBinding(){
        return BindingBuilder
                .bind(dlqQueue())
                .to(exchangedlq())
                .with(ratereviewDql);
    }
    
    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
