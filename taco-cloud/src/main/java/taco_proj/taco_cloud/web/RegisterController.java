package taco_proj.taco_cloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import taco_proj.taco_cloud.data.UserRepository;
import taco_proj.taco_cloud.entity.RegForm;


@Controller
@RequestMapping("/register")
public class RegisterController {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Use konstructor
    @Autowired
    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //or can to use annotation @RequiredArgsConstructor from Lombok

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(RegForm regForm) {
        userRepository.save(regForm.toUser(passwordEncoder));
        return "redirect:/login";
    }  
}
