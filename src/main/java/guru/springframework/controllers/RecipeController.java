package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @RequestMapping("recipe/show/{id}")
    public String showRecipe(Model model, @PathVariable String id) {
        log.info("SHOW RECIPE ID:"+id);
        model.addAttribute("recipe",  recipeService.getRecipeById(Long.valueOf(id)));
        return "recipe/show";
    }
}
