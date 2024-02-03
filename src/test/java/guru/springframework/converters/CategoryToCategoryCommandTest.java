package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 02/02/2024
 */
class CategoryToCategoryCommandTest {

    private CategoryToCategoryCommand categoryToCategoryCommand;
    @BeforeEach
    void setUp() {
        categoryToCategoryCommand = new CategoryToCategoryCommand();
    }
    @Test
    void testNullObject() {
        assertNull(categoryToCategoryCommand.convert(null));
    }
    @Test
    void testEmptyObject() {
        assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }

    @Test
    void convert() {
        Category category = new Category();
        category.setId(1L);
        category.setName("test");
        CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);
        assertNotNull(categoryCommand);
        assertEquals(1L, categoryCommand.getId());
        assertEquals("test", categoryCommand.getName());
    }
}