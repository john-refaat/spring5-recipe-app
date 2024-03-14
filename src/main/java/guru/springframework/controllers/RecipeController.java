package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.CategoryService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Set;

/**
 * @author john
 * @since 05/01/2024
 */
@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    public RecipeController(RecipeService recipeService, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.categoryService = categoryService;
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

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "recipe/recipeform";
    }

    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(Model model, @PathVariable String id) {
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(id)));
        model.addAttribute("categories", categoryService.findAllCategories());
        return "recipe/recipeform";
    }

    @PostMapping({"recipe/{id}/update", "recipe/new"})
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipe, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("recipe", recipe);
            model.addAttribute("categories", categoryService.findAllCategories());

            bindingResult.getAllErrors().forEach(objectError -> log.error("Error saving or updating: "+objectError.toString()));
            return "recipe/recipeform";
        }
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipe);
        log.info("Recipe command Saved: " + recipe);
        log.info("Cat Ids: "+ Arrays.toString(recipe.getCategoryIds()));
        return "redirect:/recipe/"+savedCommand.getId()+"/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String delete(@PathVariable String id) {
        log.info("DELETE RECIPE ID:"+id);
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/recipes";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Not Found Exception. " + exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
