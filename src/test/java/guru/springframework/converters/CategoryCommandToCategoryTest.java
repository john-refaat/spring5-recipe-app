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
class CategoryCommandToCategoryTest {

    private CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    void testNullObject() {
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommand.setName("test");
        Category category = categoryCommandToCategory.convert(categoryCommand);
        assertNotNull(category);
        assertEquals(1L, category.getId());
        assertEquals("test", category.getName());
    }
}