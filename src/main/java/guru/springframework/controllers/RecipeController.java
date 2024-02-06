package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author john
 * @since 05/01/2024
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("recipes")
    public String getRecipes(Model model) {
        log.info("GET RECIPES");
        Set<Recipe> recipes = recipeService.getRecipes();
        for (Recipe recipe : recipes) {
            System.out.println(recipe);
        }
        model.addAttribute("recipes", recipes);
        return "recipe/list";
    }

    @RequestMapping("recipe/{id}/show")
    public String showRecipe(Model model, @PathVariable String id) {
        log.info("SHOW RECIPE ID:"+id);
        model.addAttribute("recipe",  recipeService.getRecipeById(Long.valueOf(id)));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(Model model, @PathVariable String id) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
        log.info("Recipe command Saved: " + command);
        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String delete(@PathVariable String id) {
        log.info("DELETE RECIPE ID:"+id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes";
    }
}
