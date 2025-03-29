package taco_proj.taco_cloud;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Table
public class Taco {
    // checking validate (not null and min 5 chars)
    @NotNull
    @Size(min=5, message="Name must be at least 5 characters long")
    private String name;

    // checking validate (not null and min 1 Ingredient)
    @NotNull
    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<IngredientRef> ingredients;

    @Id
    private Long id;

    private Date createdAt = new Date();
}
