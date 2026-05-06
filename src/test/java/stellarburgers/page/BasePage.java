package stellarburgers.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final String baseUrl;

    protected BasePage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected void openPath(String path) {
        driver.get(baseUrl + path);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(value);
    }

    protected boolean isDisplayed(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
    }

    protected void waitUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    protected By buttonByText(String text) {
        return By.xpath("//button[normalize-space()='" + text + "' or .//*[normalize-space()='" + text + "']]");
    }

    protected By linkByText(String text) {
        return By.xpath("//a[normalize-space()='" + text + "' or .//*[normalize-space()='" + text + "']]");
    }

    protected By inputByLabel(String text) {
        return By.xpath("//input[@placeholder='" + text + "' or @name='" + text + "']"
                + " | //label[normalize-space()='" + text
                + "']/ancestor::*[contains(@class,'input') or contains(@class,'Input')][1]//input"
                + " | //label[normalize-space()='" + text + "']/following::input[1]");
    }

    @Step("Дождаться появления токена авторизации в localStorage")
    public void waitForAccessToken() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return Boolean(window.localStorage.getItem('accessToken'));")
                .equals(Boolean.TRUE));
    }
}
