package stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

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
                .statusCode(SC_OK)
                .extract()
                .path("data[0]._id");
    }
}
