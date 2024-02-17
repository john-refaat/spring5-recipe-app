package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientCommandToIngredient;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author john
 * @since 06/02/2024
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository, IngredientRepository ingredientRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        log.info("Get Ingredient service");
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            //TODO: Error Handling
            log.error("Recipe ID Not Found: " + recipeId);
            throw new RuntimeException("Recipe ID Not Found: " + recipeId);
        }
        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

        if (ingredientOptional.isEmpty()) {
            //TODO: Error Handling
            log.error("Ingredient ID Not Found: " + ingredientId);
            throw new RuntimeException("Ingredient ID Not Found: " + ingredientId);
        }

        Ingredient ingredient = ingredientOptional.get();
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
        log.info("Ingredient Command Found");
        return ingredientCommand;
    }

    @Override
    @Transactional
    public IngredientCommand saveOrUpdateIngredient(IngredientCommand ingredientCommand) {
        if (ingredientCommand == null) {
            log.error("Ingredient Command is null");
            return new IngredientCommand();
        }
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if(recipeOptional.isEmpty()) {
            //TODO: Error Handling
            log.error("Recipe ID Not Found: " + ingredientCommand.getRecipeId());
            throw new RuntimeException("Recipe ID Not Found: " + ingredientCommand.getRecipeId());
        }
        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream()
                .filter(x->x.getId().equals(ingredientCommand.getId()))
                .findFirst();

       Ingredient ingredientFound = new Ingredient();

        if(ingredientOptional.isEmpty()) {
            ingredientFound = ingredientCommandToIngredient.convert(ingredientCommand);
            if(ingredientFound!=null)
                recipe.addIngredient(ingredientFound);
        }

        if(ingredientOptional.isPresent()) {
            ingredientFound = ingredientOptional.get();
            ingredientFound.setName(ingredientCommand.getName());
            ingredientFound.setAmount(ingredientCommand.getAmount());
            if(ingredientCommand.getUom()==null || ingredientCommand.getUom().getId().equals(0L)){
                ingredientFound.setUom(null);
            } else {
                Optional<UnitOfMeasure> uomOptional = unitOfMeasureRepository.findById(ingredientCommand.getUom().getId());
                ingredientFound.setUom(uomOptional.orElseThrow(() -> new RuntimeException("UOM Not found ID: " + ingredientCommand.getUom().getId())));
            }

        }

        Ingredient savedIngredient = ingredientRepository.save(ingredientFound);
        //Recipe savedRecipe = recipeRepository.save(recipe);
        //Optional<Ingredient> savedIngredient = savedRecipe.getIngredients().stream().filter(x -> x.getId().equals(ingredientCommand.getId())).findFirst();

        return ingredientToIngredientCommand.convert(savedIngredient);
    }

    @Override
    public void deleteIngredientById(Long ingredientId) {
        log.info("Delete Ingredient ID: " + ingredientId);
        ingredientRepository.deleteById(ingredientId);
    }
}
