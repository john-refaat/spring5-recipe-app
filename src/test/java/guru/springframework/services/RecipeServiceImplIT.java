package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author john
 * @since 03/02/2024
 */
@SpringBootTest
public class RecipeServiceImplIT {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Test
    void getRecipes() {
        Set<Recipe> recipes = recipeService.getRecipes();
        assertNotNull(recipes);
    }

    @Test
    void getRecipeById() {
        Recipe recipe = recipeService.getRecipeById(1L);
        assertNotNull(recipe);
    }

    @Transactional
    @Test
    void saveRecipeCommand() {
        // Given
        Recipe testRecipe = recipeService.getRecipeById(1L);
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(testRecipe);

        // When
        String updatedName = "Updated Name";
        recipeCommand.setDescription(updatedName);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        // Then
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(updatedName, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}
