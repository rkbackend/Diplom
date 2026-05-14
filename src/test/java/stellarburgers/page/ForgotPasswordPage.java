package stellarburgers.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {
    private final By title = By.xpath("//*[normalize-space()='Восстановление пароля']");

    public ForgotPasswordPage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Step("Открыть страницу восстановления пароля")
    public ForgotPasswordPage open() {
        openPath("/forgot-password");
        waitUntilLoaded();
        return this;
    }

    @Step("Дождаться страницы восстановления пароля")
    public ForgotPasswordPage waitUntilLoaded() {
        isDisplayed(title);
        return this;
    }

    @Step("Нажать ссылку 'Войти' на форме восстановления пароля")
    public void clickLoginLink() {
        click(linkByText("Войти"));
    }
}
