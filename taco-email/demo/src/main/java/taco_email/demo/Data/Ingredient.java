package taco_email.demo.Data;

import lombok.Data;

@Data
public class Ingredient {
    private String id;
    private String name;
    private String type;

    public Ingredient(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
