package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 17/02/2024
 */
@ExtendWith(MockitoExtension.class)
class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private ImageController imageController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    void getUploadImageForm() throws Exception {
        Mockito.when(recipeService.getRecipeCommandById(ArgumentMatchers.anyLong())).thenReturn(new RecipeCommand());
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image/upload"))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.view().name("/recipe/imageUpload"));
    }

    @Test
    void uploadImage() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("image", "testing.txt",
                "text/plain", "Springframework Guru".getBytes());
        mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/1/image/upload").file(multipartFile))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/1/show"));
        Mockito.verify(imageService, Mockito.times(1)).uploadImage(ArgumentMatchers.anyLong(), ArgumentMatchers.any());
    }

    @Test
    void renderImageFromDB() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        String s = "fake image text";
        byte[] bytes = s.getBytes();
        Byte[] boxedBytes = new Byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            boxedBytes[i] = bytes[i];
        }

        recipe.setImage(boxedBytes);
        Mockito.when(recipeService.getRecipeById(ArgumentMatchers.anyLong())).thenReturn(recipe);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        byte[] byteArray = mvcResult.getResponse().getContentAsByteArray();
        assertEquals(boxedBytes.length, byteArray.length);
    }
}