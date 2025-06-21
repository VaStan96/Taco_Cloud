package taco_email.demo.Props;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="tacocloud.api")
@Component
public class ApiProperties {
    private String url;
    private String key;
}