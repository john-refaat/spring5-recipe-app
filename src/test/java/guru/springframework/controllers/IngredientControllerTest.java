package guru.springframework.controllers;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.commands.RecipeCommand;
import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.services.IngredientService;
import guru.springframework.services.RecipeService;
import guru.springframework.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 * @since 06/02/2024
 */
@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    private IngredientController ingredientController;


    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void viewIngredients() throws Exception {
        //Given
        RecipeCommand recipeCommand  = new RecipeCommand();
        recipeCommand.setId(1L);

        //When
        Mockito.when(recipeService.getRecipeCommandById(ArgumentMatchers.anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{id}/ingredients", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));

        //Then
        Mockito.verify(recipeService, Mockito.times(1)).getRecipeCommandById(ArgumentMatchers.anyLong());
    }

    @Test
    void showIngredient() throws Exception {
        //Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);

        //When
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(ingredientCommand);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{id}/ingredient/{ingredientId}/show", 1L, 2L))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/show"))
               .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));

        //Then
        Mockito.verify(ingredientService, Mockito.times(1)).findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }


    @Test
    void newIngredientForm() throws Exception {
        // Given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);
        UnitOfMeasureCommand uom1= new UnitOfMeasureCommand();
        uom1.setId(1L);
        UnitOfMeasureCommand uom2= new UnitOfMeasureCommand();
        uom2.setId(2L);
        Set<UnitOfMeasureCommand> uomSet = new HashSet<>();
        uomSet.add(uom1);
        uomSet.add(uom2);

        // When
        Mockito.when(recipeService.getRecipeCommandById(ArgumentMatchers.anyLong())).thenReturn(recipeCommand);
        Mockito.when(unitOfMeasureService.findAllUoms()).thenReturn(uomSet);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{id}/ingredient/new", 1L))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
          .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uoms"));

        // Then
        Mockito.verify(recipeService, Mockito.times(1)).getRecipeCommandById(ArgumentMatchers.anyLong());
    }

    @Test
    void updateIngredientForm() throws Exception {
        // Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(1L);
        UnitOfMeasureCommand uom1= new UnitOfMeasureCommand();
        uom1.setId(1L);
        UnitOfMeasureCommand uom2= new UnitOfMeasureCommand();
        uom2.setId(2L);
        Set<UnitOfMeasureCommand> uomSet = new HashSet<>();
        uomSet.add(uom1);
        uomSet.add(uom2);


        // When
        Mockito.when(ingredientService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(ingredientCommand);
        Mockito.when(unitOfMeasureService.findAllUoms()).thenReturn(uomSet);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{recipeId}/ingredient/{ingredientId}/update", 1L, 2L))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientForm"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uoms"));

        // Then
        Mockito.verify(ingredientService, Mockito.times(1)).findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    void saveIngredient() throws Exception {
        // Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(1L);

        // When
        Mockito.when(ingredientService.saveOrUpdateIngredient(ArgumentMatchers.any())).thenReturn(ingredientCommand);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe/{recipeId}/ingredient/", 1L))
             .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
             .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/ingredient/2/show"));

        // Then
        Mockito.verify(ingredientService, Mockito.times(1)).saveOrUpdateIngredient(ArgumentMatchers.any());
    }

    @Test
    void deleteIngredient() throws Exception {
        // Given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecipeId(1L);

        // When
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/{recipeId}/ingredient/{ingredientId}/delete", 1L, 2L))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/ingredients"));

        // Then
        Mockito.verify(ingredientService, Mockito.times(1)).deleteIngredientById(ArgumentMatchers.anyLong());
    }
}