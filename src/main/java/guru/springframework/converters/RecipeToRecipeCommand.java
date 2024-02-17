package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author john
 * @since 01/02/2024
 */
@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    final CategoryToCategoryCommand categoryToCategoryCommand;
    final IngredientToIngredientCommand ingredientToIngredientCommand;
    final NotesToNotesCommand notesToNotesCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand, IngredientToIngredientCommand ingredientToIngredientCommand, NotesToNotesCommand notesToNotesCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source==null) {
            return null;
        }
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setDescription(source.getDescription());
        Set<CategoryCommand> categoryCommands = source.getCategories().stream().map(categoryToCategoryCommand::convert).collect(Collectors.toSet());
        recipeCommand.setCategories(Arrays.copyOf(categoryCommands.toArray(), categoryCommands.size(), CategoryCommand[].class));
        Set<Long> ids = categoryCommands.stream().map(CategoryCommand::getId).collect(Collectors.toSet());
        recipeCommand.setCategoryIds(Arrays.copyOf(ids.toArray(), ids.size(), Long[].class));
        source.getIngredients().forEach(
                ingredient -> recipeCommand.getIngredients().add(ingredientToIngredientCommand.convert(ingredient)));
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setImage(source.getImage());
        recipeCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setServings(source.getServings());
        recipeCommand.setSource(source.getSource());
        return recipeCommand;
    }
}
