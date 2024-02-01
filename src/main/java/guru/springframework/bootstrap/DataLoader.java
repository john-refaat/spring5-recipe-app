package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @author john
 * @since 05/01/2024
 */
@Slf4j
@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        log.info("LOADING DATA");
        loadGuacamole();
        loadSpicyGrilledChickenTacos();


    }

    private void loadSpicyGrilledChickenTacos() {
        log.info("Loading Grilled Chicken Tacos");
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByName("Teaspoon");
        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByName("Tablespoon");
        Ingredient ancho = new Ingredient("ancho chili powder", new BigDecimal(1), tablespoon.get());
        Ingredient oregano = new Ingredient("dried oregano", new BigDecimal(1), teaspoon.get());
        Ingredient cumin = new Ingredient("dried cumin", new BigDecimal(1), teaspoon.get());
        Ingredient sugar = new Ingredient("sugar", new BigDecimal(1), teaspoon.get());
        Ingredient salt = new Ingredient("kosher salt", new BigDecimal(0.5), teaspoon.get());
        Ingredient garlic = new Ingredient("clove garlic, finely chopped", new BigDecimal(1));
        Ingredient zest = new Ingredient("finely grated orange zest", new BigDecimal(1), tablespoon.get());
        Ingredient orangeJuice = new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoon.get());
        Ingredient oil = new Ingredient("olive oil", new BigDecimal(2), tablespoon.get());
        Ingredient chicken = new Ingredient("skinless, boneless chicken thighs", new BigDecimal(4));
        Category mexican = categoryRepository.findByName("Mexican").get();
        Category american = categoryRepository.findByName("American").get();

        Recipe grilledChickenTacos = new Recipe();
        grilledChickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        grilledChickenTacos.setCookTime(15);
        grilledChickenTacos.setPrepTime(20);
        grilledChickenTacos.setServings(4);
        addCategory(grilledChickenTacos, mexican);
        addCategory(grilledChickenTacos, american);
        grilledChickenTacos.addIngredient(ancho).addIngredient(oregano).addIngredient(cumin).addIngredient(sugar)
                .addIngredient(salt).addIngredient(garlic)
                .addIngredient(zest).addIngredient(orangeJuice).addIngredient(oil).addIngredient(chicken);


        grilledChickenTacos.setDirections("Prepare the grill: Prepare either a gas or charcoal grill for medium-high, direct heat.||" +
                "Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, " +
                "salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. " +
                "Add the chicken to the bowl and toss to coat all over. Set aside to marinate while the grill heats and you prepare the rest of the toppings.||" +
                " Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165°F. Transfer to a plate and rest for 5 minutes.||" +
                "Thin the sour cream with milk: Stir together the sour cream and milk to thin out the sour cream to make it easy to drizzle.||" +
                "Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.||" +
                "Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. " +
                "As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side." +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.");

        grilledChickenTacos.setSource("Simply Recipes");
        grilledChickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        grilledChickenTacos.setDifficulty(Difficulty.EASY);
        recipeRepository.save(grilledChickenTacos);
        log.info("{} Recipe Loaded", grilledChickenTacos.getDescription());
    }

    private static void addCategory(Recipe recipe, Category category) {
        recipe.getCategories().add(category);
        //category.getRecipes().add(recipe);
    }

    private void loadGuacamole() {
        log.info("Loading Gucamole");
        Optional<UnitOfMeasure> teaspoon = unitOfMeasureRepository.findByName("Teaspoon");
        Optional<UnitOfMeasure> tablespoon = unitOfMeasureRepository.findByName("Tablespoon");
        Optional<UnitOfMeasure> pinch = unitOfMeasureRepository.findByName("Pinch");
        Ingredient avo = new Ingredient("Avocado", new BigDecimal(2));
        Ingredient salt = new Ingredient("Kosher Salt", new BigDecimal(0.25), teaspoon.get());
        Ingredient lime = new Ingredient("Fresh Lime", new BigDecimal(1), tablespoon.get());
        Ingredient onion = new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(3), tablespoon.get());
        Ingredient chilli = new Ingredient("Serrano Chilli", new BigDecimal(1));
        Ingredient cilantro = new Ingredient("Cilantro", new BigDecimal(2), tablespoon.get());
        Ingredient blackPepper = new Ingredient("Freshly ground Black Pepper", new BigDecimal(1), pinch.get());
        Ingredient tomato = new Ingredient("ripe tomato, Chopped (optional)", new BigDecimal(0.5));
        Ingredient jicma = new Ingredient("Red radish or jicama slices for garnish (optional)", new BigDecimal(1));
        Ingredient tortilla = new Ingredient("Tortilla Chips, to serve");

        Category mexican = categoryRepository.findByName("Mexican").get();
        Category american = categoryRepository.findByName("American").get();

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Guacamole");
        guacamole.setCookTime(10);
        guacamole.setPrepTime(10);
        guacamole.setServings(3);
        addCategory(guacamole, mexican);
        addCategory(guacamole, american);
        guacamole.setDifficulty(Difficulty.EASY);

        guacamole.addIngredient(avo).addIngredient(salt).addIngredient(lime).addIngredient(onion).addIngredient(chilli)
                .addIngredient(cilantro).addIngredient(blackPepper).addIngredient(tomato)
                .addIngredient(jicma).addIngredient(tortilla);


        guacamole.setDirections("Cut the avocados: Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. ||" +
                "Mash the avocado flesh: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)||" +
                "Add the remaining ingredients to taste: " +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown." +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat." +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.||" +
                "Serve immediately:" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)||" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.||" +
                "Refrigerate leftover guacamole up to 3 days.||" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        Note note = new Note();
        note.setContent("Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");
        guacamole.setNote(note);
        guacamole.setSource("Simply recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        recipeRepository.save(guacamole);
        log.info("{} Recipe Loaded", guacamole.getDescription());

    }
}
