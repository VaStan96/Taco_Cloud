package auth.auth_server;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Bean
    public ApplicationRunner dataLoader(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.save(
                new User("habuma", encoder.encode("password"), "ROLE_ADMIN"));
            repo.save(
                new User("tacochef", encoder.encode("password"), "ROLE_ADMIN"));
            };
        }   
}
