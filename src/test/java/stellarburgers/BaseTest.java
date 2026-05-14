package stellarburgers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public abstract class BaseTest {
    protected static final String BASE_URL = "https://stellarburgers.education-services.ru";
    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = createDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            clearBrowserState();
            driver.quit();
        }
    }

    private WebDriver createDriver() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--remote-allow-origins=*",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--user-data-dir=/tmp/stellar-burgers-browser-profile-" + System.nanoTime()
        );

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new", "--window-size=1920,1080");
        }

        if ("yandex".equals(browser)) {
            options.setBinary(System.getProperty("yandex.binary", "/usr/bin/yandex-browser"));
        } else if (!"chrome".equals(browser)) {
            throw new IllegalArgumentException("Unsupported browser: " + browser + ". Use chrome or yandex.");
        }

        String chromeBinary = System.getProperty("chrome.binary");
        if ("chrome".equals(browser) && chromeBinary != null && !chromeBinary.isBlank()) {
            options.setBinary(chromeBinary);
        }

        if ("yandex".equals(browser)) {
            WebDriverManager.chromedriver()
                    .browserVersion(System.getProperty("yandex.browser.version", "144"))
                    .setup();
        } else {
            WebDriverManager.chromedriver().setup();
        }
        return new ChromeDriver(options);
    }

    private void clearBrowserState() {
        try {
            driver.manage().deleteAllCookies();
            ((JavascriptExecutor) driver).executeScript("window.localStorage.clear(); window.sessionStorage.clear();");
        } catch (RuntimeException ignored) {
            // Browser can already be closing after a failed startup.
        }
    }
}
