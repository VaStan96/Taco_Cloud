package taco_proj.taco_cloud.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Data;

@Data
public class RegForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public User toUser(PasswordEncoder passwordEncoder){
        return new User(
            username, passwordEncoder.encode(password),
            fullname, street, city, state, zip, phone
        );
    }
    
}
