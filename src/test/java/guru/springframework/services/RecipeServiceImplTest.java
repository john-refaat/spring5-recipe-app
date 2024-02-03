package guru.springframework.services;

import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 15/01/2024
 */
@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> data = new HashSet<>();
        data.add(recipe);
        Mockito.when(recipeRepository.findAll()).thenReturn(data);

        Set<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getRecipeById() {
        Recipe recipe= new Recipe();
        recipe.setId(1L);

        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(recipe));

        Recipe returnedRecipe = recipeService.getRecipeById(1L);
        assertNotNull(returnedRecipe);
        assertNotNull(returnedRecipe.getId());
        assertEquals(1L, returnedRecipe.getId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(recipeRepository, Mockito.never()).findAll();


    }
}