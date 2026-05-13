package praktikum;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IngredientTest {

    @Test
    public void getTypeShouldReturnIngredientType() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "hot sauce", 50.0f);

        assertEquals(IngredientType.SAUCE, ingredient.getType());
    }

    @Test
    public void getNameShouldReturnIngredientName() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "hot sauce", 50.0f);

        assertEquals("hot sauce", ingredient.getName());
    }

    @Test
    public void getPriceShouldReturnIngredientPrice() {
        Ingredient ingredient = new Ingredient(IngredientType.SAUCE, "hot sauce", 50.0f);

        assertEquals(50.0f, ingredient.getPrice(), 0.001f);
    }
}
