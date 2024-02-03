package guru.springframework.converters;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 03/02/2024
 */
class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final Long ID = 1L;
    private static final String UNIT_OF_MEASURE = "unitOfMeasure";
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void convertNullObject() {
        assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(null));
    }

    @Test
    void convertEmptyObject() {
        assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure()));

    }
    @Test
    void convert() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID);
        unitOfMeasure.setName(UNIT_OF_MEASURE);
        UnitOfMeasureCommand unitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure);
        assertEquals(ID, unitOfMeasureCommand.getId());
        assertEquals(UNIT_OF_MEASURE, unitOfMeasureCommand.getName());
    }
}