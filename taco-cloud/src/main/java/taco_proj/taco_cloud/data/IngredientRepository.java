package taco_proj.taco_cloud.data;

import org.springframework.data.repository.CrudRepository;

import taco_proj.taco_cloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
    
}
