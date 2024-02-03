package guru.springframework.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author john
 * @since 03/01/2024
 */
@Getter
@EqualsAndHashCode(exclude = {"ingredients"})
@Entity
public class Recipe {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String description;
    @Setter
    private Integer prepTime;
    @Setter
    private Integer cookTime;
    @Setter
    private Integer servings;
    @Setter
    private String source;
    @Setter
    private String url;

    @Setter
    @Lob
    private String directions;

    @Setter
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Setter
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Setter
    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @Setter
    @ManyToMany
    @JoinTable(name = "recipe_category",
            joinColumns =@JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", prepTime=" + prepTime +
                ", cookTime=" + cookTime +
                ", servings=" + servings +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", directions='" + directions + '\'' +
                ", ingredients=" + ingredients +
                ", difficulty=" + difficulty +
                ", note=" + notes +
                ", categories=" + categories +
                '}';
    }

    public Recipe addIngredient(Ingredient ingredient){
        ingredient.setRecipe(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public void setNotes(Notes notes) {
        if (notes !=null) {
            notes.setRecipe(this);
            this.notes = notes;
        }
    }

}
