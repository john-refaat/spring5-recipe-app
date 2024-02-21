package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * @author john
 * @since 17/02/2024
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void uploadImage(Long id, MultipartFile file) {
        log.info("Uploading image for recipe ID: " + id);
        log.info("Uploading  "+ file.getName());
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isEmpty()) {
            //TODO: Error Handling
            log.error("Recipe ID Not Found: " + id);
            throw new RuntimeException("Recipe ID Not Found: " + id);
        }
        Recipe recipe = recipeOptional.get();
        try {


            Byte[] bytes = new Byte[file.getBytes().length];
            for (int i = 0; i < file.getBytes().length; i++) {
                bytes[i] = file.getBytes()[i];
            }
            recipe.setImage(bytes);

        } catch (IOException e) {
            //TODO: Error Handling
            log.error(e.getMessage());
        }
        recipeRepository.save(recipe);
    }

}
