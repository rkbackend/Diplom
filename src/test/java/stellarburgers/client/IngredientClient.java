package stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class IngredientClient {

    private static final String INGREDIENTS_PATH = "/ingredients";

    @Step("Get ingredients")
    public Response getIngredients() {
        return given()
                .when()
                .get(INGREDIENTS_PATH);
    }

    @Step("Get first ingredient id")
    public String getFirstIngredientId() {
        return getIngredients()
                .then()
                .statusCode(200)
                .extract()
                .path("data[0]._id");
    }
}
