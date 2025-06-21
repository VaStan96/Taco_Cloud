package taco_email.demo.Props;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix="tacocloud.email")
@Component
public class EmailProperties {
    private String username;
    private String password;
    private String host;
    private String mailbox;
    private long pollRate = 30000;

    public String getImapUrl(){
        String url = String.format("imaps://%s:%s@%s/%s", 
                                    URLEncoder.encode(this.username, StandardCharsets.UTF_8), URLEncoder.encode(this.password, StandardCharsets.UTF_8), 
                                    this.host, this.mailbox);
        return url;
    }
}
