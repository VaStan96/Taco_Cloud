package taco_proj.taco_cloud.web;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import taco_proj.taco_cloud.Ingredient.Type;
import taco_proj.taco_cloud.data.IngredientRepository;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    // Easy way to implement a controller with a View "home page"
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home");
    }

    // Implementation of the "ApplicationRunner" interface for creating data before launching the application
    // Since the interface is functional, the 'run()' method is implemented as a lambda function.
    // It is annotated as a 'Bean' component because it returns a lambda function
    // alternative: using the file data.sql
    @Bean
    public ApplicationRunner dataloader(IngredientRepository ingredientRepository){
        return args -> {
            ingredientRepository.insert("FLTO", "Flour Tortilla", Type.WRAP);
            ingredientRepository.insert("COTO", "Corn Tortilla", Type.WRAP);
            ingredientRepository.insert("GRBF", "Ground Beef", Type.PROTEIN);
            ingredientRepository.insert("CARN", "Carnitas", Type.PROTEIN);
            ingredientRepository.insert("TMTO", "Diced Tomatoes", Type.VEGGIES);
            ingredientRepository.insert("LETC", "Lettuce", Type.VEGGIES);
            ingredientRepository.insert("CHED", "Cheddar", Type.CHEESE);
            ingredientRepository.insert("JACK", "Monterrey Jack", Type.CHEESE);
            ingredientRepository.insert("SLSA", "Salsa", Type.SAUCE);
            ingredientRepository.insert("SRCR", "Sour Cream", Type.SAUCE);
        };
    }
}
