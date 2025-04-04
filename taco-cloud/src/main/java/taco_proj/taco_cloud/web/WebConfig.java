package taco_proj.taco_cloud.web;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import taco_proj.taco_cloud.Ingredient;
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
            ingredientRepository.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
            ingredientRepository.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
            ingredientRepository.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
            ingredientRepository.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
            ingredientRepository.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
            ingredientRepository.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
            ingredientRepository.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
            ingredientRepository.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
            ingredientRepository.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
            ingredientRepository.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
        };
    }
}
