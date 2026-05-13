package stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import stellarburgers.client.UserClient;
import stellarburgers.model.UserCreateRequest;

import java.util.Arrays;
import java.util.Collection;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateUserRequiredFieldsTest extends BaseTest {

    private static final String REQUIRED_FIELDS_MESSAGE = "Email, password and name are required fields";

    private final UserCreateRequest incompleteUser;

    private final UserClient userClient = new UserClient();

    public CreateUserRequiredFieldsTest(UserCreateRequest incompleteUser) {
        this.incompleteUser = incompleteUser;
    }

    @Parameterized.Parameters(name = "missing required field #{index}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new UserCreateRequest(null, "password123", "QA User")},
                {new UserCreateRequest("qa_missing_email@example.com", null, "QA User")},
                {new UserCreateRequest("qa_missing_name@example.com", "password123", null)}
        });
    }

    @Test
    @DisplayName("Create user without required field")
    @Description("User registration fails when one required field is missing")
    public void createUserWithoutRequiredFieldShouldReturnError() {
        userClient.create(incompleteUser)
                .then()
                .statusCode(SC_FORBIDDEN)
                .body("success", equalTo(false))
                .body("message", equalTo(REQUIRED_FIELDS_MESSAGE));
    }
}
