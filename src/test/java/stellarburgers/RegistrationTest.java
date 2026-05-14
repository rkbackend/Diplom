package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import stellarburgers.client.UserApiClient;
import stellarburgers.model.UserCredentials;
import stellarburgers.page.LoginPage;
import stellarburgers.page.RegisterPage;
import stellarburgers.util.TestUserFactory;

@Feature("Регистрация")
public class RegistrationTest extends BaseTest {
    private final UserApiClient userApiClient = new UserApiClient();
    private UserCredentials createdUser;

    @After
    public void deleteCreatedUser() {
        if (createdUser != null) {
            userApiClient.deleteUser(createdUser);
        }
    }

    @Test
    @DisplayName("Успешная регистрация пользователя")
    @Description("Проверка успешной регистрации нового пользователя с валидными именем, email и паролем.")
    public void shouldRegisterUserSuccessfully() {
        createdUser = TestUserFactory.randomUser();

        new RegisterPage(driver, BASE_URL).open()
                .register(createdUser.getName(), createdUser.getEmail(), createdUser.getPassword());

        new LoginPage(driver, BASE_URL).waitUntilLoaded();
        Assert.assertTrue("После успешной регистрации должен открыться экран входа",
                driver.getCurrentUrl().contains("/login"));
    }

    @Test
    @DisplayName("Ошибка при регистрации с паролем короче 6 символов")
    @Description("Проверка появления ошибки при попытке регистрации с паролем короче шести символов.")
    public void shouldShowErrorForShortPassword() {
        UserCredentials user = TestUserFactory.randomUser();
        RegisterPage registerPage = new RegisterPage(driver, BASE_URL).open();

        registerPage.register(user.getName(), user.getEmail(), "12345");

        Assert.assertTrue("Для пароля короче 6 символов должна появиться ошибка",
                registerPage.isIncorrectPasswordErrorVisible());
    }
}
