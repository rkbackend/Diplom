package stellarburgers.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {
    private final By profileHint = By.xpath("//*[contains(normalize-space(),'изменить свои персональные данные')]");

    public ProfilePage(WebDriver driver, String baseUrl) {
        super(driver, baseUrl);
    }

    @Step("Дождаться открытия личного кабинета")
    public boolean isProfileOpened() {
        waitUrlContains("/account");
        return isDisplayed(profileHint);
    }
}
