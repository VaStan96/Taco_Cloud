package taco_proj.taco_cloud;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Type type;

    public enum Type{
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }
} 

// Нам также понадобится @RequiredArgsConstructor. Аннотация
// @Data неявно добавляет конструктор с обязательными аргументами,
// но, когда используется @NoArgsConstructor, этот конструктор уда-
// ляется. Явное добавление аннотации @RequiredArgsConstructor га-
// рантирует, что мы по-прежнему будем иметь конструктор со всеми
// обязательными аргументами, помимо приватного конструктора без
// аргументов.
