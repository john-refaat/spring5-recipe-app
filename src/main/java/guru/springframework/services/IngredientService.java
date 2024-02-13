package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

/**
 * @author john
 * @since 06/02/2024
 */
public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand);
}
