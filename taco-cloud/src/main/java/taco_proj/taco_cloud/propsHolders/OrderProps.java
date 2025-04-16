package taco_proj.taco_cloud.propsHolders;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Component
//allows you to configure properties with prefix via the file "application.yaml"
@ConfigurationProperties(prefix="taco.orders")
@Data
@Validated
public class OrderProps {
    // "application.yaml" uses this default value
    @Min(value=5, message="must be between 5 and 25")
    @Max(value=25, message="must be between 5 and 25")
    private int pageSize = 20;
}
