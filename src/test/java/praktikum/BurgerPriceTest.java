package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class BurgerPriceTest {

    private final float bunPrice;
    private final float[] ingredientPrices;
    private final float expectedPrice;

    public BurgerPriceTest(float bunPrice, float[] ingredientPrices, float expectedPrice) {
        this.bunPrice = bunPrice;
        this.ingredientPrices = ingredientPrices;
        this.expectedPrice = expectedPrice;
    }

    @Parameterized.Parameters(name = "bun={0}, ingredients count={1}, expected={2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {100.0f, new float[]{}, 200.0f},
                {100.0f, new float[]{50.0f}, 250.0f},
                {100.0f, new float[]{50.0f, 75.0f}, 325.0f}
        });
    }

    @Test
    public void getPriceShouldReturnDoubleBunPricePlusIngredientPrices() {
        Burger burger = new Burger();
        Bun bun = mock(Bun.class);
        when(bun.getPrice()).thenReturn(bunPrice);
        burger.setBuns(bun);

        for (float ingredientPrice : ingredientPrices) {
            Ingredient ingredient = mock(Ingredient.class);
            when(ingredient.getPrice()).thenReturn(ingredientPrice);
            burger.addIngredient(ingredient);
        }

        assertEquals(expectedPrice, burger.getPrice(), 0.001f);
    }
}
