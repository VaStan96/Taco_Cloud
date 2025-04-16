package taco_proj.taco_cloud.web;

import java.util.Arrays;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import taco_proj.taco_cloud.Ingredient;
import taco_proj.taco_cloud.Ingredient.Type;
import taco_proj.taco_cloud.Taco;
import taco_proj.taco_cloud.User;
import taco_proj.taco_cloud.data.IngredientRepository;
import taco_proj.taco_cloud.data.TacoRepository;
import taco_proj.taco_cloud.data.UserRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    // Easy way to implement a controller with a View "home page"
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home");
        registry.addViewController("login").setViewName("login");
    }

    // Implementation of the "ApplicationRunner" interface for creating data before launching the application
    // Since the interface is functional, the 'run()' method is implemented as a lambda function.
    // It is annotated as a 'Bean' component because it returns a lambda function
    // alternative: using the file data.sql
    @Bean
    // This component will be created if the "prod" or "qa" profile is not enabled
    @Profile({"!prod", "!qa"})
    public ApplicationRunner dataloader(IngredientRepository ingredientRepository, 
                                        UserRepository userRepository, 
                                        TacoRepository tacoRepository, 
                                        PasswordEncoder passwordEncoder){
        return args -> {

            // default Ingredients
            Ingredient flourTortilla = new Ingredient(
                "FLTO", "Flour Tortilla", Type.WRAP);
            Ingredient cornTortilla = new Ingredient(
                "COTO", "Corn Tortilla", Type.WRAP);
            Ingredient groundBeef = new Ingredient(
                "GRBF", "Ground Beef", Type.PROTEIN);
            Ingredient carnitas = new Ingredient(
                "CARN", "Carnitas", Type.PROTEIN);
            Ingredient tomatoes = new Ingredient(
                "TMTO", "Diced Tomatoes", Type.VEGGIES);
            Ingredient lettuce = new Ingredient(
                "LETC", "Lettuce", Type.VEGGIES);
            Ingredient cheddar = new Ingredient(
                "CHED", "Cheddar", Type.CHEESE);
            Ingredient jack = new Ingredient(
                "JACK", "Monterrey Jack", Type.CHEESE);
            Ingredient salsa = new Ingredient(
                "SLSA", "Salsa", Type.SAUCE);
            Ingredient sourCream = new Ingredient(
                "SRCR", "Sour Cream", Type.SAUCE);
    
            ingredientRepository.save(flourTortilla);
            ingredientRepository.save(cornTortilla);
            ingredientRepository.save(groundBeef);                
            ingredientRepository.save(carnitas);
            ingredientRepository.save(tomatoes);
            ingredientRepository.save(lettuce);
            ingredientRepository.save(cheddar);
            ingredientRepository.save(jack);
            ingredientRepository.save(salsa);
            ingredientRepository.save(sourCream);

            // default Tacos
            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Arrays.asList(
                flourTortilla, groundBeef, carnitas,
                sourCream, salsa, cheddar));
            tacoRepository.save(taco1);
            
            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Arrays.asList(
                cornTortilla, groundBeef, cheddar,
                jack, sourCream));
            tacoRepository.save(taco2);
            
            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Arrays.asList(
                flourTortilla, cornTortilla, tomatoes,
                lettuce, salsa));
            tacoRepository.save(taco3);

            // default Users
            userRepository.save(new User("user1", passwordEncoder.encode("user1"), 
                                        "User1 Users", "Street 1", 
                                        "City 1", "State 1", "123456", 
                                        "1234567890"));
            userRepository.save(new User("user2", passwordEncoder.encode("user2"), 
                                        "User2 Users", "Street 2", 
                                        "City 2", "State 2", "123456", 
                                        "1234567890"));
        };
    }
}
