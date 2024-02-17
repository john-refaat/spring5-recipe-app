package guru.springframework.services;

import guru.springframework.commands.CategoryCommand;

import java.util.Set;

/**
 * @author john
 * @since 14/02/2024
 */
public interface CategoryService {
    Set<CategoryCommand> findAllCategories();
}
