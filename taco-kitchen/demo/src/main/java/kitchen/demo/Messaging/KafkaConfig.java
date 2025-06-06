package kitchen.demo.Messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.JsonMessageConverter;

@Configuration
public class KafkaConfig {
    @Bean
    public JsonMessageConverter messageConverter() {
        return new JsonMessageConverter();
    }
}
