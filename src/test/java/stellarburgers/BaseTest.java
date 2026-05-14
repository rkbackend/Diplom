package stellarburgers;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import stellarburgers.client.UserClient;
import stellarburgers.model.UserCreateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class BaseTest {

    protected static final String BASE_URI = "https://stellarburgers.education-services.ru/api";

    private final UserClient userClient = new UserClient();
    private final List<String> accessTokensToDelete = new ArrayList<>();

    @Before
    public void setUpBaseUri() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 10000)
                        .setParam("http.socket.timeout", 10000));
    }

    @After
    public void cleanUpUsers() {
        for (String accessToken : accessTokensToDelete) {
            userClient.delete(accessToken);
        }
        accessTokensToDelete.clear();
    }

    protected UserCreateRequest uniqueUser() {
        String uniquePart = UUID.randomUUID().toString();
        return new UserCreateRequest(
                "qa_" + uniquePart + "@example.com",
                "password123",
                "QA User"
        );
    }

    protected String registerAndRememberToken(UserCreateRequest user) {
        Response response = userClient.create(user);
        String accessToken = response.path("accessToken");
        rememberToken(accessToken);
        return accessToken;
    }

    protected void rememberToken(String accessToken) {
        if (accessToken != null && !accessToken.isBlank()) {
            accessTokensToDelete.add(accessToken);
        }
    }
}
