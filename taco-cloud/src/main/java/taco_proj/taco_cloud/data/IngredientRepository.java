package taco_proj.taco_cloud.data;

import java.util.Optional;

import taco_proj.taco_cloud.Ingredient;

public interface IngredientRepository {
    
    Iterable<Ingredient> findAll();
    
    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);

}
