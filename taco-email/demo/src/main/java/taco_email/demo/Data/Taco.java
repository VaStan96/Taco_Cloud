package taco_email.demo.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Taco {
    
    private Long id;
    private String name;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Date createdAt = new Date();

    public Taco(String tacoName) {
        this.name = tacoName;
    }
}
