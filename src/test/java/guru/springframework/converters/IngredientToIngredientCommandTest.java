package guru.springframework.converters;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 02/02/2024
 */
class IngredientToIngredientCommandTest {

    private final Long INGRED_ID = new Long(1L);
    private final String INGRED_NAME = "test";
    private final String INGRED_UNIT_OF_MEASURE = "test";
    private final BigDecimal INGRED_AMOUNT = new BigDecimal(2.0);
    private final Long INGRED_UNIT_OF_MEASURE_ID = new Long(2L);

    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @BeforeEach
    void setUp() {
        ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());

    }

    @Test
    void testNullObject() {
        assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
    }

    @Test
    void convert() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID);
        ingredient.setName(INGRED_NAME);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(INGRED_UNIT_OF_MEASURE_ID);
        uom.setName(INGRED_UNIT_OF_MEASURE);

        ingredient.setUom(uom);
        ingredient.setAmount(INGRED_AMOUNT);

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        assertNotNull(ingredientCommand);
        assertEquals(INGRED_ID, ingredientCommand.getId());
        assertEquals(INGRED_NAME, ingredientCommand.getName());
        assertEquals(INGRED_AMOUNT, ingredientCommand.getAmount());
        assertEquals(INGRED_UNIT_OF_MEASURE_ID, ingredientCommand.getUom().getId());
        assertEquals(INGRED_UNIT_OF_MEASURE, ingredientCommand.getUom().getName());
    }

    @Test
    void convertWithNullUom() {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID);
        ingredient.setName(INGRED_NAME);

        ingredient.setAmount(INGRED_AMOUNT);

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        assertNotNull(ingredientCommand);
        assertEquals(INGRED_ID, ingredientCommand.getId());
        assertEquals(INGRED_NAME, ingredientCommand.getName());
        assertEquals(INGRED_AMOUNT, ingredientCommand.getAmount());
        assertNull(ingredientCommand.getUom());
    }
}