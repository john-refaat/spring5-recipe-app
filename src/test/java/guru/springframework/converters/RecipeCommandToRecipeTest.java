package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.NotesCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 02/02/2024
 */
class RecipeCommandToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;

    private RecipeCommandToRecipe recipeCommandToRecipe;


    @BeforeEach
    void setUp() {
        recipeCommandToRecipe = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new CategoryCommandToCategory());
    }

    @Test
    void testNullObject() {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
        assertEquals(new Recipe(), recipeCommandToRecipe.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        CategoryCommand e1 = new CategoryCommand();
        e1.setId(CAT_ID_1);
        CategoryCommand e2 = new CategoryCommand();
        e2.setId(CAT_ID2);
        recipeCommand.setCategories(new CategoryCommand[]{e1, e2});

        IngredientCommand i = new IngredientCommand();
        i.setId(INGRED_ID_1);
        recipeCommand.getIngredients().add(i);
        i = new IngredientCommand();
        i.setId(INGRED_ID_2);
        recipeCommand.getIngredients().add(i);
        NotesCommand n = new NotesCommand();
        n.setId(NOTES_ID);
        recipeCommand.setNotes(n);

        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertNotNull(recipe.getCategories());
        assertEquals(2, recipe.getCategories().size());
        assertNotNull(recipe.getIngredients());
        assertEquals(2, recipe.getIngredients().size());
        assertNotNull(recipe.getNotes());
        assertEquals(NOTES_ID, recipe.getNotes().getId());

    }

}