package praktikum;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BunTest {

    @Test
    public void getNameShouldReturnBunName() {
        Bun bun = new Bun("white bun", 100.0f);

        assertEquals("white bun", bun.getName());
    }

    @Test
    public void getPriceShouldReturnBunPrice() {
        Bun bun = new Bun("white bun", 100.0f);

        assertEquals(100.0f, bun.getPrice(), 0.001f);
    }
}
