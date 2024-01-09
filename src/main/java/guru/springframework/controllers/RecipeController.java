package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

/**
 * @author john
 * @since 05/01/2024
 */
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("recipes")
    public String getRecipes(Model model){
        Set<Recipe> recipes = recipeService.getRecipes();
        for(Recipe recipe: recipes) {
            System.out.println(recipe);
        }
        model.addAttribute("recipes", recipes );
        return "recipe/list";
    }
}
