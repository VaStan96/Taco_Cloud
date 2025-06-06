package taco_proj.taco_cloud.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String QUEUE_NAME = "kitchens.central";
    public static final String EXCHANGE_NAME = "tacocloud.order";

    @Bean
    public Queue orderQueue(){
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange orderExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue orderQueue, TopicExchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with("kitchens.#");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
    
    // ---Converter for Object to Message---
    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        Jackson2JsonMessageConverter messageConverter = new Jackson2JsonMessageConverter();
        return messageConverter;
    }


}
