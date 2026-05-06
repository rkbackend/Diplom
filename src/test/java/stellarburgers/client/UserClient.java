package stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stellarburgers.model.UserCreateRequest;
import stellarburgers.model.UserLoginRequest;

import static io.restassured.RestAssured.given;

public class UserClient {

    private static final String REGISTER_PATH = "/auth/register";
    private static final String LOGIN_PATH = "/auth/login";
    private static final String USER_PATH = "/auth/user";

    @Step("Create user")
    public Response create(UserCreateRequest user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(REGISTER_PATH);
    }

    @Step("Login user")
    public Response login(UserLoginRequest user) {
        return given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Delete user")
    public Response delete(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .when()
                .delete(USER_PATH);
    }
}
