package taco_proj.taco_cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import taco_proj.taco_cloud.data.UserRepository;
import taco_proj.taco_cloud.entity.User;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        // Type for password encoding (NoOpPasswordEncoder, StandardPasswordEncoder, BCryptPasswordEncoder)
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    // UserDetailsService is functional interface
    // wir return lambda-function
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .authorizeHttpRequests((authorizeHttpRequest) ->
            authorizeHttpRequest
            .requestMatchers(HttpMethod.POST, "/api/ingredients").hasAuthority("SCOPE_writeIngredients")
            .requestMatchers(HttpMethod.DELETE, "/api/ingredients").hasAuthority("SCOPE_deleteIngredients")
            .requestMatchers(HttpMethod.GET, "/api/orders").hasAuthority("SCOPE_getOrders")
            .requestMatchers(HttpMethod.POST, "/api/orders").hasAuthority("SCOPE_postOrders")
            .requestMatchers(HttpMethod.PUT, "/api/orders").hasAuthority("SCOPE_putOrders")
            .requestMatchers(HttpMethod.PATCH, "/api/orders").hasAuthority("SCOPE_patchOrders")
            .requestMatchers(HttpMethod.DELETE, "/api/orders").hasAuthority("SCOPE_deleteOrders")
            .requestMatchers(HttpMethod.POST, "/orders/fromEmail").permitAll()
            .requestMatchers("/design", "/orders").hasRole("USER")
            .requestMatchers("/", "/**", "/h2-console/**").permitAll()
        )
        .formLogin((formLogin) ->
            formLogin
            .loginPage("/login")
            .defaultSuccessUrl("/design")
        )
        .logout(withDefaults())
        .csrf((csrf)->
            csrf
            .ignoringRequestMatchers("/h2-console/**", "/api/**")
        )
        .headers((headers)->
            headers.frameOptions(frame->frame.disable())
        )
        .oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("http://localhost:9000/oauth2/jwks").build();
    }
}
