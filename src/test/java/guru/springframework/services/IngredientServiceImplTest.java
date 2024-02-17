package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 07/02/2024
 */
@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    private static final String NAME = "<NAME>";
    private static final Long RECIPE_ID = 2L;
    private static final Long INGREDIENT_ID = 1L;
    public static final BigDecimal AMOUNT = new BigDecimal("2.00");

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientCommandToIngredient ingredientCommandToIngredient;

    //@InjectMocks
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
        ingredientService = new IngredientServiceImpl(recipeRepository, ingredientToIngredientCommand, ingredientCommandToIngredient, unitOfMeasureRepository, ingredientRepository);
    }

    @Test
    void findByRecipeIdAndIngredientIdHappyPath() {
        // Given
        Long recipeId = 1L;
        Optional<Recipe> recipeOptional = getRecipeOptional(recipeId);

        // When
        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(recipeOptional);

        IngredientCommand returnedIngredientCommand = ingredientService.findByRecipeIdAndIngredientId(recipeId, 3L);

        // Then
        assertNotNull(returnedIngredientCommand);
        assertEquals(3L, returnedIngredientCommand.getId());
        assertEquals(recipeId, returnedIngredientCommand.getRecipeId());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
    }

    @Test
    public void saveIngredientCommand() throws Exception {
        //Given

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setRecipeId(RECIPE_ID);
        ingredientCommand.setName(NAME);
        ingredientCommand.setAmount(AMOUNT);

        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGREDIENT_ID);
        recipe.addIngredient(ingredient);


        Ingredient savedIngredient = new Ingredient();
        savedIngredient.setId(INGREDIENT_ID);
        savedIngredient.setName(NAME);
        savedIngredient.setAmount(AMOUNT);

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(RECIPE_ID);
        savedRecipe.addIngredient(savedIngredient);


        //When
        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(recipe));
       // Mockito.when(recipeRepository.save(ArgumentMatchers.any(Recipe.class))).thenReturn(savedRecipe);
        Mockito.when(ingredientRepository.save(ArgumentMatchers.any(Ingredient.class))).thenReturn(savedIngredient);

        IngredientCommand returnedIngredientCommand = ingredientService.saveOrUpdateIngredient(ingredientCommand);

        //Then
        assertNotNull(returnedIngredientCommand);
        assertEquals(INGREDIENT_ID, returnedIngredientCommand.getId());
        assertEquals(RECIPE_ID, returnedIngredientCommand.getRecipeId());
        assertEquals(NAME, returnedIngredientCommand.getName());
        assertEquals(AMOUNT, returnedIngredientCommand.getAmount());
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(unitOfMeasureRepository, Mockito.never()).findById(ArgumentMatchers.anyLong());
    }



    private Optional<Recipe> getRecipeOptional(Long recipeId) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient1.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient1.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        return Optional.of(recipe);
    }

    @Test
    public void deleteIngredientCommand() {
        //Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGREDIENT_ID);
        ingredientCommand.setRecipeId(RECIPE_ID);

        //When
        ingredientService.deleteIngredientById(ingredientCommand.getId());

        //Then
        Mockito.verify(ingredientRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }
}