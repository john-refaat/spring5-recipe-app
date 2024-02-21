package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author john
 * @since 18/02/2024
 */
@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    private ImageService imageService;

    @BeforeEach
    void setUp() {
        imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    void uploadImage() throws IOException {
        //Given
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("image.png", "image.png", "image/png", "image".getBytes());
        Recipe recipe = new Recipe();
        recipe.setId(id);
        //When
        Mockito.when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(recipe));

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        imageService.uploadImage(id, file);

        //Then
        Mockito.verify(recipeRepository, Mockito.times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(file.getBytes().length, savedRecipe.getImage().length);
    }
}