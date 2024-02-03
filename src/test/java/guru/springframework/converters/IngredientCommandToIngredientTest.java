package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 02/02/2024
 */
class IngredientCommandToIngredientTest {

    private final Long INGRED_ID = new Long(1L);
    private final String INGRED_NAME = "test";
    private final String INGRED_DESCRIPTION = "test";
    private final String INGRED_UNIT_OF_MEASURE = "test";
    private final BigDecimal INGRED_AMOUNT = new BigDecimal(2.0);
    private final Long INGRED_UNIT_OF_MEASURE_ID = new Long(2L);


    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @BeforeEach
    void setUp() {
        ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullObject() {
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGRED_ID);
        ingredientCommand.setName(INGRED_NAME);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(INGRED_UNIT_OF_MEASURE_ID);
        unitOfMeasureCommand.setName(INGRED_UNIT_OF_MEASURE);
        ingredientCommand.setUom(unitOfMeasureCommand);
        ingredientCommand.setAmount(INGRED_AMOUNT);
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        assertNotNull(ingredient);
        assertEquals(INGRED_ID, ingredient.getId());
        assertEquals(INGRED_NAME, ingredient.getName());
        assertEquals(INGRED_AMOUNT, ingredient.getAmount());
        assertEquals(INGRED_UNIT_OF_MEASURE_ID, ingredient.getUom().getId());
        assertEquals(INGRED_UNIT_OF_MEASURE, ingredient.getUom().getName());
    }

    @Test
    void convertWithNullUom() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(INGRED_ID);
        ingredientCommand.setName(INGRED_NAME);
        ingredientCommand.setAmount(INGRED_AMOUNT);
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
        assertNotNull(ingredient);
        assertEquals(INGRED_ID, ingredient.getId());
        assertEquals(INGRED_NAME, ingredient.getName());
        assertEquals(INGRED_AMOUNT, ingredient.getAmount());
        assertNull(ingredient.getUom());
    }
}