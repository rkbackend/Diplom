package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Test;
import stellarburgers.page.MainPage;

@Feature("Конструктор")
public class ConstructorTest extends BaseTest {

    @Test
    @DisplayName("Переход к разделу 'Соусы'")
    @Description("Проверка, что клик по табу 'Соусы' делает этот раздел активным в конструкторе.")
    public void shouldSwitchToSaucesSection() {
        MainPage mainPage = new MainPage(driver, BASE_URL).open();

        mainPage.clickConstructorTab("Соусы");

        Assert.assertTrue("Активным должен стать раздел 'Соусы'",
                mainPage.isConstructorTabActive("Соусы"));
    }

    @Test
    @DisplayName("Переход к разделу 'Начинки'")
    @Description("Проверка, что клик по табу 'Начинки' делает этот раздел активным в конструкторе.")
    public void shouldSwitchToFillingsSection() {
        MainPage mainPage = new MainPage(driver, BASE_URL).open();

        mainPage.clickConstructorTab("Начинки");

        Assert.assertTrue("Активным должен стать раздел 'Начинки'",
                mainPage.isConstructorTabActive("Начинки"));
    }

    @Test
    @DisplayName("Переход к разделу 'Булки'")
    @Description("Проверка возврата к разделу 'Булки' после перехода в другой раздел конструктора.")
    public void shouldSwitchBackToBunsSection() {
        MainPage mainPage = new MainPage(driver, BASE_URL).open();
        mainPage.clickConstructorTab("Начинки");

        mainPage.clickConstructorTab("Булки");

        Assert.assertTrue("Активным должен стать раздел 'Булки'",
                mainPage.isConstructorTabActive("Булки"));
    }
}
