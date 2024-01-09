package guru.springframework.repositories;

import guru.springframework.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

/**
 * @author john
 * @since 04/01/2024
 */
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
