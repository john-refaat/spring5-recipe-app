package guru.springframework.services;

import guru.springframework.domain.Recipe;

import java.util.Set;

/**
 * @author john
 * @since 06/01/2024
 */
public interface RecipeService {
    Set<Recipe> getRecipes();
}
