package stellarburgers.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    private final By title = By.xpath("//*[normalize-space()='Регистрация']");
    private final By incorrectPasswordError = By.xpath("//*[normalize-space()='Некорректный пароль']");

    public RegisterPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Step("Открыть страницу регистрации")
    public RegisterPage open() {
        openPath("/register");
        waitUntilLoaded();
        return this;
    }

    @Step("Дождаться страницы регистрации")
    public RegisterPage waitUntilLoaded() {
        isDisplayed(title);
        return this;
    }

    @Step("Зарегистрировать пользователя {email}")
    public void register(String name, String email, String password) {
        type(inputByLabel("Имя"), name);
        type(inputByLabel("Email"), email);
        type(inputByLabel("Пароль"), password);
        click(buttonByText("Зарегистрироваться"));
    }

    @Step("Нажать ссылку 'Войти' на форме регистрации")
    public void clickLoginLink() {
        click(linkByText("Войти"));
    }

    @Step("Проверить ошибку некорректного пароля")
    public boolean isIncorrectPasswordErrorVisible() {
        return isDisplayed(incorrectPasswordError);
    }
}
