package taco_proj.taco_cloud.web;

// Get String with Ingredient-ID and return object Ingredient

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import taco_proj.taco_cloud.Ingredient;
import taco_proj.taco_cloud.IngredientUDT;
import taco_proj.taco_cloud.TacoUDTUtils;
import taco_proj.taco_cloud.data.IngredientRepository;

@Component
public class IngredientByIdConverter implements Converter<String, IngredientUDT>{
    
    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientByIdConverter(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientUDT convert(String id){
        Ingredient ingredient = ingredientRepository.findById(id).orElse(null);
        if (ingredient != null){
            return TacoUDTUtils.toIngredientUDT(ingredient);
        }
        return null;
    }
}