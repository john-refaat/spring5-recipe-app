package guru.springframework.converters;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 * @author john
 * @since 01/02/2024
 */
@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesCommandToNotes;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final CategoryCommandToCategory categoryCommandToCategory;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes, IngredientCommandToIngredient ingredientCommandToIngredient,
                                 CategoryCommandToCategory categoryCommandToCategory) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.categoryCommandToCategory = categoryCommandToCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source==null) {
            return null;
        }
        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setServings(source.getServings());
        recipe.setDirections(source.getDirections());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setImage(source.getImage());
        if(source.getCategories()!=null) {
            Stream.of(source.getCategories()).forEach(categoryCommand -> {
                recipe.getCategories().add(categoryCommandToCategory.convert(categoryCommand));
            });
        }

        source.getIngredients().forEach(ingredientCommand -> {
            recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        });
        return recipe;
    }

}
