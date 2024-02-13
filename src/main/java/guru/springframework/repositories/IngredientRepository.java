package guru.springframework.repositories;

import guru.springframework.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

/**
 * @author john
 * @since 07/02/2024
 */
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}
