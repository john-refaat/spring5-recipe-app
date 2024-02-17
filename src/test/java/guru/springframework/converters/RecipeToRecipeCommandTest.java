package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 02/02/2024
 */
class RecipeToRecipeCommandTest {

    private RecipeToRecipeCommand recipeToRecipeCommand;

    private static final Long RECIPE_ID = 1L;
    private static final Integer PREP_TIME = 10;
    private static final Integer COOK_TIME = 20;
    private static final String DESCRIPTION = "some description";
    private static final String DIRECTIONS = "some directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;
    private static final Integer SERVINGS = 3;
    private static final String SOURCE = "some source";
    private static final String URL = "some url";
    private static final Long CAT_ID_1 = 1L;
    private static final Long CAT_ID2 = 2L;
    private static final Long INGRED_ID_1 = 1L;
    private static final Long INGRED_ID_2 = 2L;
    public static final Long NOTES_ID = 9L;


    @BeforeEach
    void setUp() {
        recipeToRecipeCommand   = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand());
    }

    @Test
    void testNullObject() {
        assertNull(recipeToRecipeCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
    }

    @Test
    void convert() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        Category category = new Category();
        category.setId(CAT_ID_1);
        recipe.getCategories().add(category);
        category = new Category();
        category.setId(CAT_ID2);
        recipe.getCategories().add(category);
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);
        recipe.getIngredients().add(ingredient);
        ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_2);
        recipe.getIngredients().add(ingredient);
        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
        assertNotNull(recipeCommand);
        assertEquals(RECIPE_ID, recipeCommand.getId());
        assertNotNull(recipeCommand.getCategories());
        assertNotNull(recipeCommand.getIngredients());
        assertEquals(2, recipeCommand.getCategories().length);
        assertEquals(2, recipeCommand.getIngredients().size());
        assertNotNull(recipeCommand.getNotes());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
    }
}