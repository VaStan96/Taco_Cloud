package taco_proj.taco_cloud.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import jakarta.jms.ConnectionFactory;

@Configuration
public class MessagingConfig {
    
    // ---Default path in queue as object---
    // @Bean
    // public Destination orderQueue(){
    //     return new ActiveMQQueue("tacocloud.order.queue");
    // }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory){
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
    
    // ---Converter for Object to Message---
    @Bean
    public MappingJackson2MessageConverter messageConverter(){
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTargetType(MessageType.TEXT);
        messageConverter.setTypeIdPropertyName("_type");

        return messageConverter;
    }
}
