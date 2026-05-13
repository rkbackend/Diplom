package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import stellarburgers.client.IngredientClient;
import stellarburgers.client.OrderClient;
import stellarburgers.model.OrderRequest;
import stellarburgers.model.UserCreateRequest;

import java.util.Collections;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTest extends BaseTest {

    private final IngredientClient ingredientClient = new IngredientClient();
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Create authorized order with ingredients")
    @Description("An authorized user can create an order with a valid ingredient id")
    public void createOrderWithAuthorizationShouldBeSuccessful() {
        UserCreateRequest user = uniqueUser();
        String accessToken = registerAndRememberToken(user);
        OrderRequest order = orderWithFirstIngredient();

        orderClient.create(order, accessToken)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Create guest order with ingredients")
    @Description("An unauthorized user can create an order with a valid ingredient id")
    public void createOrderWithoutAuthorizationShouldBeSuccessful() {
        OrderRequest order = orderWithFirstIngredient();

        orderClient.create(order)
                .then()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .body("order.number", notNullValue());
    }

    @Test
    @DisplayName("Create order without ingredients")
    @Description("Order creation fails when the ingredients array is empty")
    public void createOrderWithoutIngredientsShouldReturnError() {
        orderClient.create(new OrderRequest(Collections.emptyList()))
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Create order with invalid ingredient hash")
    @Description("Order creation with an invalid ingredient id returns a server error")
    public void createOrderWithInvalidIngredientHashShouldReturnServerError() {
        orderClient.create(new OrderRequest(Collections.singletonList("bad_hash")))
                .then()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                .body(containsString("Internal Server Error"));
    }

    private OrderRequest orderWithFirstIngredient() {
        return new OrderRequest(Collections.singletonList(ingredientClient.getFirstIngredientId()));
    }
}
