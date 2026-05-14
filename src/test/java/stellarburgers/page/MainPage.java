package stellarburgers.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {
    private final By title = By.xpath("//*[normalize-space()='Соберите бургер']");
    private final By personalAccountLink = By.xpath("//a[normalize-space()='Личный Кабинет' or .//*[normalize-space()='Личный Кабинет']]");

    public MainPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Step("Открыть главную страницу")
    public MainPage open() {
        openPath("/");
        waitUntilLoaded();
        return this;
    }

    @Step("Дождаться загрузки главной страницы")
    public MainPage waitUntilLoaded() {
        isDisplayed(title);
        return this;
    }

    @Step("Нажать кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        click(buttonByText("Войти в аккаунт"));
    }

    @Step("Нажать 'Личный Кабинет'")
    public void clickPersonalAccount() {
        click(personalAccountLink);
    }

    @Step("Перейти к разделу конструктора: {tabName}")
    public void clickConstructorTab(String tabName) {
        click(tabByText(tabName));
    }

    @Step("Проверить, что активен раздел конструктора: {tabName}")
    public boolean isConstructorTabActive(String tabName) {
        WebElement tab = wait.until(driver -> {
            WebElement element = driver.findElement(tabByText(tabName));
            return element.getAttribute("class").contains("tab_type_current") ? element : null;
        });
        return tab.getAttribute("class").contains("tab_type_current");
    }

    private By tabByText(String tabName) {
        return By.xpath("//*[contains(@class,'tab') and (normalize-space()='" + tabName
                + "' or .//*[normalize-space()='" + tabName + "'])]");
    }
}
