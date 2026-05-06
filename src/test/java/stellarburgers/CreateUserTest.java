package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import stellarburgers.client.UserClient;
import stellarburgers.model.UserCreateRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest extends BaseTest {

    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Create unique user")
    @Description("A unique user can be registered successfully")
    public void createUniqueUserShouldBeSuccessful() {
        UserCreateRequest user = uniqueUser();

        String accessToken = userClient.create(user)
                .then()
                .statusCode(200)
                .body("success", equalTo(true))
                .body("accessToken", notNullValue())
                .body("refreshToken", notNullValue())
                .body("user.email", equalTo(user.getEmail()))
                .body("user.name", equalTo(user.getName()))
                .extract()
                .path("accessToken");

        rememberToken(accessToken);
    }

    @Test
    @DisplayName("Create duplicate user")
    @Description("Registering the same user twice returns an error")
    public void createDuplicateUserShouldReturnError() {
        UserCreateRequest user = uniqueUser();
        registerAndRememberToken(user);

        userClient.create(user)
                .then()
                .statusCode(403)
                .body("success", equalTo(false))
                .body("message", equalTo("User already exists"));
    }
}
