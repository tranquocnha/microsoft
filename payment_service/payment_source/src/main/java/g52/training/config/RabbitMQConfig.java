package g52.training.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${microservice_training.rabbitmq.queue}")
    String queueName;

    @Bean
    Queue queue() {
        return QueueBuilder.durable("Payment_Demo").withArgument("x-dead-letter-exchange", "payment-dlq-exchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter").build();
    }

    @Bean
    Queue dlq() {
        return QueueBuilder.durable("Payment_DLQ_DEMO").build();
    }

    @Value("${microservice_training.rabbitmq.exchange}")
    String exchange;

    @Value("${microservice_training.rabbitmq.routingkey}")
    private String routingkey;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange("payment-dlq-exchange");
    }

    @Bean
    Binding DLQbinding() {
        return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
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
    //create MessageListenerContainer using default connection factory
//    @Bean
//    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
//        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//        simpleMessageListenerContainer.setQueues(queue());
//        simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
//        return simpleMessageListenerContainer;
//
//    }

    //create custom connection factory
	/*@Bean
	ConnectionFactory connectionFactory() {
		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
		cachingConnectionFactory.setUsername(username);
		cachingConnectionFactory.setUsername(password);
		return cachingConnectionFactory;
	}*/

    //create MessageListenerContainer using custom connection factory
	/*@Bean
	MessageListenerContainer messageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
		simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
		simpleMessageListenerContainer.setQueues(queue());
		simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
		return simpleMessageListenerContainer;

	}*/
}
