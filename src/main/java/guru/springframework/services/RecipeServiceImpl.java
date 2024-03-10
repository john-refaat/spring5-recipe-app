package guru.springframework.services;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.converters.CategoryToCategoryCommand;
import guru.springframework.converters.RecipeCommandToRecipe;
import guru.springframework.converters.RecipeToRecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author john
 * @since 06/01/2024
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand, CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Transactional
    @Override
    public Set<Recipe> getRecipes() {
        log.info("Get Recipe service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).orElseThrow(()->new NotFoundException("Recipe not found for ID: " + id));
    }

    @Transactional
    @Override
    public RecipeCommand getRecipeCommandById(Long id) {
        Recipe recipe = getRecipeById(id);
        return recipeToRecipeCommand.convert(recipe);
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Set<Long> ids = new HashSet<>();
        if(recipeCommand.getCategoryIds()!=null)
            ids.addAll(Arrays.asList(recipeCommand.getCategoryIds()));

        Set<CategoryCommand> categoryCommands = new HashSet<>();
        if(recipeCommand.getCategories()!=null)
            categoryCommands.addAll(Arrays.asList(recipeCommand.getCategories()));

        categoryRepository.findAll().forEach(x ->{
            if(ids.contains(x.getId())){
                categoryCommands.add(categoryToCategoryCommand.convert(x));
            }
        });
        recipeCommand.setCategories(categoryCommands.toArray(new CategoryCommand[categoryCommands.size()]));
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        RecipeCommand savedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe);
        return savedRecipeCommand;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        log.info("Delete Recipe "+id);
        recipeRepository.deleteById(id);
    }


}
