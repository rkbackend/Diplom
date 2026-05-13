package praktikum;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BurgerTest {

    @Test
    public void setBunsShouldSetBurgerBun() {
        Burger burger = new Burger();
        Bun bun = mock(Bun.class);

        burger.setBuns(bun);

        assertEquals(bun, burger.bun);
    }

    @Test
    public void addIngredientShouldIncreaseIngredientsCount() {
        Ingredient ingredient = mock(Ingredient.class);
        Burger burger = burgerWithIngredient(ingredient);

        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void addIngredientShouldAddIngredientToBurger() {
        Ingredient ingredient = mock(Ingredient.class);
        Burger burger = burgerWithIngredient(ingredient);

        assertEquals(ingredient, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientShouldDecreaseIngredientsCount() {
        Ingredient remainingIngredient = mock(Ingredient.class);
        Burger burger = burgerAfterRemovingFirstIngredient(remainingIngredient);

        assertEquals(1, burger.ingredients.size());
    }

    @Test
    public void removeIngredientShouldLeaveSecondIngredient() {
        Ingredient remainingIngredient = mock(Ingredient.class);
        Burger burger = burgerAfterRemovingFirstIngredient(remainingIngredient);

        assertEquals(remainingIngredient, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientShouldMoveSecondIngredientToFirstIndex() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);
        Burger burger = burgerAfterMovingFirstIngredientToEnd(firstIngredient,
                secondIngredient, thirdIngredient);

        assertEquals(secondIngredient, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientShouldMoveThirdIngredientToSecondIndex() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);
        Burger burger = burgerAfterMovingFirstIngredientToEnd(firstIngredient,
                secondIngredient, thirdIngredient);

        assertEquals(thirdIngredient, burger.ingredients.get(1));
    }

    @Test
    public void moveIngredientShouldMoveFirstIngredientToLastIndex() {
        Ingredient firstIngredient = mock(Ingredient.class);
        Ingredient secondIngredient = mock(Ingredient.class);
        Ingredient thirdIngredient = mock(Ingredient.class);
        Burger burger = burgerAfterMovingFirstIngredientToEnd(firstIngredient,
                secondIngredient, thirdIngredient);

        assertEquals(firstIngredient, burger.ingredients.get(2));
    }

    @Test
    public void getReceiptShouldReturnFormattedReceipt() {
        Burger burger = new Burger();
        Bun bun = mock(Bun.class);
        Ingredient sauce = mock(Ingredient.class);
        Ingredient filling = mock(Ingredient.class);

        when(bun.getName()).thenReturn("black bun");
        when(bun.getPrice()).thenReturn(100.0f);
        when(sauce.getType()).thenReturn(IngredientType.SAUCE);
        when(sauce.getName()).thenReturn("hot sauce");
        when(sauce.getPrice()).thenReturn(50.0f);
        when(filling.getType()).thenReturn(IngredientType.FILLING);
        when(filling.getName()).thenReturn("cutlet");
        when(filling.getPrice()).thenReturn(75.0f);

        burger.setBuns(bun);
        burger.addIngredient(sauce);
        burger.addIngredient(filling);

        String expectedReceipt = String.format("(==== black bun ====)%n"
                + "= sauce hot sauce =%n"
                + "= filling cutlet =%n"
                + "(==== black bun ====)%n"
                + "%n"
                + "Price: 325.000000%n");

        assertEquals(expectedReceipt, burger.getReceipt());
    }

    private Burger burgerWithIngredient(Ingredient ingredient) {
        Burger burger = new Burger();
        burger.addIngredient(ingredient);

        return burger;
    }

    private Burger burgerAfterRemovingFirstIngredient(Ingredient remainingIngredient) {
        Burger burger = new Burger();
        Ingredient removedIngredient = mock(Ingredient.class);
        burger.addIngredient(removedIngredient);
        burger.addIngredient(remainingIngredient);

        burger.removeIngredient(0);

        return burger;
    }

    private Burger burgerAfterMovingFirstIngredientToEnd(Ingredient firstIngredient,
                                                        Ingredient secondIngredient,
                                                        Ingredient thirdIngredient) {
        Burger burger = new Burger();
        burger.addIngredient(firstIngredient);
        burger.addIngredient(secondIngredient);
        burger.addIngredient(thirdIngredient);

        burger.moveIngredient(0, 2);

        return burger;
    }
}
