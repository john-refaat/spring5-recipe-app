package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.ImageService;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author john
 * @since 17/02/2024
 */
@Slf4j
@Controller
public class ImageController {

    private final ImageService imageService;

    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/image/upload")
    public String getUploadImageForm(@PathVariable String id, Model model) {
        log.info("Showing upload image form for recipe ID: " + id);
        model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(id)));
        return "/recipe/imageUpload";
    }

    @PostMapping("/recipe/{id}/image/upload")
    public String uploadImage(@PathVariable String id, @RequestParam("image") MultipartFile file) {
        log.info("Uploading image for recipe ID: " + id);
        log.info("Uploading "+ file.getName());
        imageService.uploadImage(Long.valueOf(id), file);
        return "redirect:/recipe/"+id+"/show";
    }

    @GetMapping("/recipe/{id}/image/show")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) {
        log.info("Rendering image for recipe ID: " + id);
        Recipe recipe = recipeService.getRecipeById(Long.valueOf(id));
        response.setContentType("image/jpeg");
        if (recipe.getImage() == null) {
            return;
        }
        try {
            Byte[] image = recipe.getImage();
            byte[] byteArray = new byte[image.length];
            for (int i = 0; i < image.length; i++) {
                byteArray[i] = image[i];
            }
            InputStream in = new ByteArrayInputStream(byteArray);
            IOUtils.copy(in, response.getOutputStream());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
