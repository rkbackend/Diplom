package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import stellarburgers.client.UserClient;
import stellarburgers.model.UserCreateRequest;
import stellarburgers.model.UserLoginRequest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginUserTest extends BaseTest {

    private final UserClient userClient = new UserClient();
    private UserCreateRequest user;
    private String accessToken;

    @Before
    public void createUser() {
        user = uniqueUser();
        accessToken = registerAndRememberToken(user);
        assertThat(accessToken, notNullValue());
    }

    @Test
    @DisplayName("Login existing user")
    @Description("A registered user can log in with valid credentials")
    public void loginExistingUserShouldBeSuccessful() {
        userClient.login(new UserLoginRequest(user.getEmail(), user.getPassword()))
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()));
    }

    @Test
    @DisplayName("Login with wrong email")
    @Description("Login with invalid email and valid password returns an authorization error")
    public void loginWithWrongEmailShouldReturnError() {
        userClient.login(new UserLoginRequest("wrong-user@example.com", user.getPassword()))
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login with wrong password")
    @Description("Login with valid email and invalid password returns an authorization error")
    public void loginWithWrongPasswordShouldReturnError() {
        userClient.login(new UserLoginRequest(user.getEmail(), "wrong-password"))
                .then()
                .statusCode(SC_UNAUTHORIZED)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}
