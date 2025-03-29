package taco_proj.taco_cloud.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import taco_proj.taco_cloud.Ingredient;

@Repository
public class JDBCIngredientRepository implements IngredientRepository {

    private JdbcOperations jdbcOperations;

    @Autowired
    public JDBCIngredientRepository (JdbcOperations jdbcOperations){
        this.jdbcOperations = jdbcOperations;
    }


    @Override
    public Iterable<Ingredient> findAll() {
        return jdbcOperations.query("SELECT id, name, type FROM Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Optional<Ingredient> findById(String id) {
        List<Ingredient> results = jdbcOperations.query(
            "SELECT id, name, type FROM Ingredient WHERE id=?",
            this::mapRowToIngredient,
            id
        );
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbcOperations.update(
            "INSERT INTO Ingredient (id, name, type) VALUES (?,?,?)",
            ingredient.getId(),
            ingredient.getName(),
            ingredient.getType().toString()
        );
        return (ingredient);
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException{
        return new Ingredient(
            row.getString("id"),
            row.getString("name"),
            Ingredient.Type.valueOf(row.getString("type"))
        );
    }
    
}
