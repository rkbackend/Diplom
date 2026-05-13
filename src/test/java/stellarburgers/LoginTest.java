package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.client.UserApiClient;
import stellarburgers.model.UserCredentials;
import stellarburgers.page.ForgotPasswordPage;
import stellarburgers.page.LoginPage;
import stellarburgers.page.MainPage;
import stellarburgers.page.ProfilePage;
import stellarburgers.page.RegisterPage;
import stellarburgers.util.TestUserFactory;

@Feature("Вход")
public class LoginTest extends BaseTest {
    private final UserApiClient userApiClient = new UserApiClient();
    private UserCredentials user;

    @Before
    public void createUser() {
        user = TestUserFactory.randomUser();
        userApiClient.createUser(user);
    }

    @After
    public void deleteUser() {
        if (user != null) {
            userApiClient.deleteUser(user);
        }
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт' на главной странице")
    @Description("Проверка успешного входа пользователя через кнопку 'Войти в аккаунт' на главной странице.")
    public void shouldLoginFromMainLoginButton() {
        MainPage mainPage = new MainPage(driver, BASE_URL).open();
        mainPage.clickLoginButton();

        new LoginPage(driver, BASE_URL).waitUntilLoaded()
                .login(user.getEmail(), user.getPassword());

        assertUserLoggedIn();
    }

    @Test
    @DisplayName("Вход через кнопку 'Личный Кабинет'")
    @Description("Проверка успешного входа пользователя через кнопку 'Личный Кабинет' в шапке сайта.")
    public void shouldLoginFromPersonalAccountButton() {
        MainPage mainPage = new MainPage(driver, BASE_URL).open();
        mainPage.clickPersonalAccount();

        new LoginPage(driver, BASE_URL).waitUntilLoaded()
                .login(user.getEmail(), user.getPassword());

        assertUserLoggedIn();
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации")
    @Description("Проверка успешного входа пользователя после перехода из формы регистрации.")
    public void shouldLoginFromRegistrationForm() {
        new RegisterPage(driver, BASE_URL).open()
                .clickLoginLink();

        new LoginPage(driver, BASE_URL).waitUntilLoaded()
                .login(user.getEmail(), user.getPassword());

        assertUserLoggedIn();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля")
    @Description("Проверка успешного входа пользователя после перехода из формы восстановления пароля.")
    public void shouldLoginFromForgotPasswordForm() {
        new ForgotPasswordPage(driver, BASE_URL).open()
                .clickLoginLink();

        new LoginPage(driver, BASE_URL).waitUntilLoaded()
                .login(user.getEmail(), user.getPassword());

        assertUserLoggedIn();
    }

    private void assertUserLoggedIn() {
        new LoginPage(driver, BASE_URL).waitForAccessToken();
        MainPage mainPage = new MainPage(driver, BASE_URL);
        mainPage.clickPersonalAccount();

        Assert.assertTrue("После входа должен открываться личный кабинет",
                new ProfilePage(driver, BASE_URL).isProfileOpened());
    }
}
