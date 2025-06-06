package kitchen.demo.Messaging;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kitchen.demo.TacoOrder;

@Configuration
public class MessagingConf {
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jacksonConverter());
        return template;
    }

    @Bean
    public Jackson2JsonMessageConverter jacksonConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        typeMapper.setTrustedPackages("taco_proj.taco_cloud");
        
        Map<String, Class<?>> idMappings = new HashMap<>();
        idMappings.put("taco_proj.taco_cloud.TacoOrder", TacoOrder.class);
        typeMapper.setIdClassMapping(idMappings);

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}
