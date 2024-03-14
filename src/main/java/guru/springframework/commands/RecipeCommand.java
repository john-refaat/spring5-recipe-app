package guru.springframework.commands;

import guru.springframework.domain.Difficulty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 * @since 01/02/2024
 */
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotBlank
    @Size(min=3, max=255)
    private String description;

    @Min(2)
    @Max(999)
    private Integer prepTime;

    @Min(2)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(999)
    private Integer servings;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Byte[] image;
    private CategoryCommand[] categories;
    private Long[] categoryIds;
}
