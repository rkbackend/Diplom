package stellarburgers.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    private final By title = By.xpath("//*[normalize-space()='Вход']");

    public LoginPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Step("Открыть страницу входа")
    public LoginPage open() {
        openPath("/login");
        waitUntilLoaded();
        return this;
    }

    @Step("Дождаться страницы входа")
    public LoginPage waitUntilLoaded() {
        isDisplayed(title);
        return this;
    }

    @Step("Войти с email {email}")
    public void login(String email, String password) {
        type(inputByLabel("Email"), email);
        type(inputByLabel("Пароль"), password);
        click(buttonByText("Войти"));
    }

    @Step("Перейти на регистрацию")
    public void clickRegisterLink() {
        click(linkByText("Зарегистрироваться"));
    }

    @Step("Перейти на восстановление пароля")
    public void clickForgotPasswordLink() {
        click(linkByText("Восстановить пароль"));
    }
}
