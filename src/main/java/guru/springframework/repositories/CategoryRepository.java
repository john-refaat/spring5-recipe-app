package guru.springframework.repositories;

import guru.springframework.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author john
 * @since 04/01/2024
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

   Optional<Category> findByName(String name);
}
