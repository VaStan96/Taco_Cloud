package taco_email.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.core.GenericTransformer;
import org.springframework.stereotype.Component;

import taco_email.demo.Data.EmailOrder;
import taco_email.demo.Data.Ingredient;
import taco_email.demo.Data.RawEmailMessage;
import taco_email.demo.Data.Taco;


@Component
public class EmailToOrderTransformer implements GenericTransformer<RawEmailMessage, EmailOrder>{

    @Override
    public EmailOrder transform(RawEmailMessage message){
        String from = message.getFrom();
        String content = message.getContent();
        return parseEmailToOrder(from, content);
    }

    private EmailOrder parseEmailToOrder(String email, String content) {
        
        EmailOrder order = new EmailOrder(email);
        String[] lines = content.split("\\r?\\n");

        for (String line : lines) {
            if (line.trim().length() > 0 && line.contains(":")) {
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                
                List<Ingredient> ingredientsList= new ArrayList<>();
                for (String ingredientName : ingredientsSplit) {
                    Ingredient ingr = lookupIngredientName(ingredientName.trim());
                    if (ingr != null) {
                        ingredientsList.add(ingr);
                    }
                }
                Taco taco = new Taco(tacoName);
                taco.setIngredients(ingredientsList);
                order.addTaco(taco);
            }
        }
        return order;
    }


    private Ingredient lookupIngredientName(String ingredientName) {
        String ucIngredientName = ingredientName.toUpperCase();
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            if (ucIngredientName.equals(ingredient.getName())) {
                return ingredient;
            }
        }
        return null;
    }

    private final static Ingredient[] ALL_INGREDIENTS = new Ingredient[] {
        new Ingredient("FLTO", "FLOUR TORTILLA", "WRAP"),
        new Ingredient("COTO", "CORN TORTILLA", "WRAP"),
        new Ingredient("GRBF", "GROUND BEEF", "PROTEIN"),
        new Ingredient("CARN", "CARNITAS", "PROTEIN"),
        new Ingredient("TMTO", "TOMATOES", "VEGGIES"),
        new Ingredient("LETC", "LETTUCE", "VEGGIES"),
        new Ingredient("CHED", "CHEDDAR", "CHEESE"),
        new Ingredient("JACK", "MONTERREY JACK", "CHEESE"),
        new Ingredient("SLSA", "SALSA", "SAUCE"),
        new Ingredient("SRCR", "SOUR CREAM", "SAUCE")
    };
}
