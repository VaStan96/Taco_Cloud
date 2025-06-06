package client.taco_client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
        .authorizeHttpRequests(auth->
            auth
                .requestMatchers(HttpMethod.GET, "/admin/home","/admin/ingredients").permitAll()
                .anyRequest().authenticated())
        .oauth2Login(oauth2Login -> 
            oauth2Login.loginPage("/oauth2/authorization/taco-admin-client"))
        .oauth2Client(Customizer.withDefaults()); 
        return http.build();
    }
}
