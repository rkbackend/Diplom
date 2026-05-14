package stellarburgers.client;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import stellarburgers.model.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient {

    private static final String ORDERS_PATH = "/orders";

    @Step("Create order without authorization")
    public Response create(OrderRequest order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }

    @Step("Create order with authorization")
    public Response create(OrderRequest order, String accessToken) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDERS_PATH);
    }
}
