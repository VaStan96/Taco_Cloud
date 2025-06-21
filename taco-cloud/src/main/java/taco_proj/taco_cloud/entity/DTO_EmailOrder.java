package taco_proj.taco_cloud.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DTO_EmailOrder {
    private String userName;
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}
