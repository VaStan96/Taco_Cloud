package taco_proj.taco_cloud.data;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import taco_proj.taco_cloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    // Modification for initial filling of the table with ingredients
    // The save() method is not suitable because when passing the id it uses update(), not insert()
    @Modifying
    @Query("INSERT INTO ingredient (id, name, type) VALUES (:id, :name, :type)")
    void insert(@Param("id") String id, @Param("name") String name, @Param("type") Ingredient.Type type);
}
