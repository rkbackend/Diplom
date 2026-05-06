package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import stellarburgers.client.UserClient;
import stellarburgers.model.UserCreateRequest;
import stellarburgers.model.UserLoginRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest {

    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Login existing user")
    @Description("A registered user can log in with valid credentials")
    public void loginExistingUserShouldBeSuccessful() {
        UserCreateRequest user = uniqueUser();
        registerAndRememberToken(user);

        userClient.login(new UserLoginRequest(user.getEmail(), user.getPassword()))
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()));
    }

    @Test
    @DisplayName("Login with wrong credentials")
    @Description("Login with invalid email and password returns an authorization error")
    public void loginWithWrongCredentialsShouldReturnError() {
        userClient.login(new UserLoginRequest("wrong-user@example.com", "wrong-password"))
                .then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
