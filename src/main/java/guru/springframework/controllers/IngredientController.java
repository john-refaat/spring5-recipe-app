package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author john
 * @since 06/02/2024
 */
@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String viewIngredients(Model model, @PathVariable String id) {
        log.info("GET INGREDIENTS FOR RECIPE ID: " + id);
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(id)));
        return "recipe/ingredient/list";
    }
    @GetMapping("/recipe/{id}/ingredient/{ingredientId}/show")
    public String viewIngredient(Model model, @PathVariable String id, @PathVariable String ingredientId) {
        log.info("GET INGREDIENT ID: " + ingredientId + "of Recipe Id:" + id);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(id), Long.valueOf(ingredientId)));
        return "recipe/ingredient/show";
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredient/new")
    public String newRecipeIngredient(Model model, @PathVariable String id) {
        log.info("NEW INGREDIENT FOR RECIPE ID: " + id);
        RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(id));
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeCommand.getId());
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uoms", unitOfMeasureService.findAllUoms());
        return "recipe/ingredient/ingredientForm";
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {
        log.info("UPDATE INGREDIENT ID: " + ingredientId + " of Recipe Id:" + recipeId);
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uoms", unitOfMeasureService.findAllUoms());
        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdateIngredient(@PathVariable String recipeId, @ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedCommand = ingredientService.saveOrUpdateIngredient(ingredientCommand);
        log.info("Saved Ingredient ID: " + ingredientCommand.getId() +" Recipe ID: " + ingredientCommand.getRecipeId());
        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";
    }

    @GetMapping("/recipe/{id}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(Model model, @PathVariable String id, @PathVariable String ingredientId) {
        log.info("DELETE INGREDIENT ID: " + ingredientId + "of Recipe Id:" + id);
        ingredientService.deleteIngredientById(Long.valueOf(ingredientId));
        return "redirect:/recipe/"+id+"/ingredients";
    }

}
