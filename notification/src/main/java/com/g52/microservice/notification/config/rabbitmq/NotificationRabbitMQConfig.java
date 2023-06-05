package com.g52.microservice.notification.config.rabbitmq;

import com.g52.microservice.notification.constant.NotificationConstant;
import com.g52.microservice.notification.model.shared.constant.NotificationSharedConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationRabbitMQConfig {
  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
      SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setMessageConverter(jsonConverter());
    configurer.configure(factory, connectionFactory);

    return factory;
  }

  @Bean
  public RabbitTemplate jsonRabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate template = new RabbitTemplate(connectionFactory);
    template.setMessageConverter(jsonConverter());

    return template;
  }

  @Bean
  public MessageConverter jsonConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public RabbitAdmin getRabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  // create exchange
  @Bean
  public TopicExchange notifyTopicExchange() {
    return new TopicExchange(NotificationSharedConstant.NOTIFY_TOPIC_EXCHANGE);
  }

  @Bean
  public TopicExchange notifyDeadLetterTopicExchange() {
    return new TopicExchange(NotificationConstant.NOTIFY_DL_TOPIC_EXCHANGE);
  }

  // for order payment
  @Bean
  public Queue orderPaymentTopicQueue() {
    return QueueBuilder.durable(NotificationConstant.NOTIFY_ORDER_PAYMENT_TOPIC_QUEUE)
        .withArgument("x-dead-letter-exchange", NotificationConstant.NOTIFY_DL_TOPIC_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", NotificationSharedConstant.NOTIFY_ORDER_PAYMENT_ROUTING_KEY)
        .build();
  }

  @Bean
  public Queue orderPaymentDeadLetterTopicQueue() {
    return QueueBuilder.durable(NotificationConstant.NOTIFY_ORDER_PAYMENT_DL_TOPIC_QUEUE).build();
  }

  @Bean
  public Binding notityOrderPaymentTopicBinding() {
    return BindingBuilder.bind(orderPaymentTopicQueue())
        .to(notifyTopicExchange()).with(NotificationSharedConstant.NOTIFY_ORDER_PAYMENT_ROUTING_KEY);
  }

  @Bean
  public Binding notityOrderPaymentDeadLetterTopicBinding() {
    return BindingBuilder.bind(orderPaymentDeadLetterTopicQueue())
        .to(notifyDeadLetterTopicExchange()).with(NotificationSharedConstant.NOTIFY_ORDER_PAYMENT_ROUTING_KEY);
  }


  // for mail
  @Bean
  public Queue mailTopicQueue() {
    return QueueBuilder.durable(NotificationConstant.NOTIFY_MAIL_TOPIC_QUEUE)
        .withArgument("x-dead-letter-exchange", NotificationConstant.NOTIFY_DL_TOPIC_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", NotificationSharedConstant.NOTIFY_MAIL_ROUTING_KEY)
        .build();
  }

  @Bean
  public Queue mailDeadLetterTopicQueue() {
    return QueueBuilder.durable(NotificationConstant.NOTIFY_MAIL_DL_TOPIC_QUEUE).build();
  }

  @Bean
  public Binding notityMailTopicBinding() {
    return BindingBuilder.bind(mailTopicQueue())
        .to(notifyTopicExchange()).with(NotificationSharedConstant.NOTIFY_MAIL_ROUTING_KEY);
  }

  @Bean
  public Binding notityMailDeadLetterTopicBinding() {
    return BindingBuilder.bind(mailDeadLetterTopicQueue())
        .to(notifyDeadLetterTopicExchange()).with(NotificationSharedConstant.NOTIFY_MAIL_ROUTING_KEY);
  }

  // for others
}
