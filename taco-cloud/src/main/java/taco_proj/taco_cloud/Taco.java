package taco_proj.taco_cloud;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Taco {
    // checking validate (not null and min 5 chars)
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    // checking validate (not null and min 1 Ingredient)
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients = new ArrayList<>();

    private Date createdAt = new Date();

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
}
