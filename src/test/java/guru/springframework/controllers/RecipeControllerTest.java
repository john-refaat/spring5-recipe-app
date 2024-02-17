package guru.springframework.controllers;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.CategoryService;
import guru.springframework.services.RecipeService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 16/01/2024
 */
@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    private RecipeController recipeController;

    @Mock
    private Model model;

    @Mock
    private RecipeService recipeService;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        recipeController = new RecipeController(recipeService, categoryService);
        mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

    }

    @Test
    void testMockMVC() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/list"));
    }

    @Test
    void getRecipes() {
        Set<Recipe> data = new HashSet<>();
        data.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        data.add(recipe);

        ArgumentCaptor<Set> captor = ArgumentCaptor.forClass(Set.class);
        Mockito.when(recipeService.getRecipes()).thenReturn(data);
        String response = recipeController.getRecipes(model);
        assertEquals("recipe/list", response);
        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipes"), captor.capture());
        assertEquals(2, captor.getValue().size());
    }

    @Test
    void getRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Recipe matchRecipe = new Recipe();
        matchRecipe.setId(1L);

        Mockito.when(recipeService.getRecipeById(ArgumentMatchers.anyLong())).thenReturn(recipe);
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
                .andExpect(MockMvcResultMatchers.model().attribute("recipe",  matchRecipe));
        Mockito.verify(recipeService, Mockito.times(1)).getRecipeById(ArgumentMatchers.anyLong());
        Mockito.verify(recipeService, Mockito.never()).getRecipes();

    }

    @Test
    void getNewRecipeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
                .andExpect(MockMvcResultMatchers.model().attribute("recipe", Matchers.equalTo(new RecipeCommand())));
    }

    @Test
    void getUpdateRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.getRecipeCommandById(ArgumentMatchers.anyLong())).thenReturn(command);
        Mockito.when(categoryService.findAllCategories()).thenReturn(new HashSet<CategoryCommand>());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+command.getId()+"/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
                .andExpect(MockMvcResultMatchers.model().attribute("recipe", Matchers.equalTo(command)));

        Mockito.verify(recipeService, Mockito.times(1)).getRecipeCommandById(ArgumentMatchers.anyLong());
        Mockito.verify(categoryService, Mockito.times(1)).findAllCategories();
    }

    @Test
    void saveOrUpdate() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        Mockito.when(recipeService.saveRecipeCommand(ArgumentMatchers.any(RecipeCommand.class))).thenReturn(command);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe")
              .param("id", "2")
              .param("name", "Pizza")
              .param("description", "Pizza description"))
              .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
              .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/2/show"));

        Mockito.verify(recipeService, Mockito.times(1)).saveRecipeCommand(ArgumentMatchers.any(RecipeCommand.class));
    }

    @Test
    void delete() throws Exception {
        long idToDelete = 2L;

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+idToDelete+"/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipes"));

        Mockito.verify(recipeService, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }
}