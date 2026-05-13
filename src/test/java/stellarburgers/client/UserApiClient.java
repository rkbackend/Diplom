package stellarburgers.client;

import com.google.gson.Gson;
import stellarburgers.model.UserCredentials;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

public class UserApiClient {
    private static final String API_URL = "https://stellarburgers.education-services.ru/api";
    private final Gson gson = new Gson();
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void createUser(UserCredentials user) {
        sendJson("/auth/register", "POST", gson.toJson(user), null);
    }

    public Optional<String> login(UserCredentials user) {
        HttpResponse<String> response = sendJson("/auth/login", "POST",
                gson.toJson(new LoginRequest(user.getEmail(), user.getPassword())),
                null);
        LoginResponse loginResponse = gson.fromJson(response.body(), LoginResponse.class);
        return Optional.ofNullable(loginResponse)
                .map(LoginResponse::getAccessToken);
    }

    public void deleteUser(UserCredentials user) {
        login(user).ifPresent(this::deleteByToken);
    }

    private void deleteByToken(String accessToken) {
        sendJson("/auth/user", "DELETE", "", accessToken);
    }

    private HttpResponse<String> sendJson(String path, String method, String body, String token) {
        try {
            HttpRequest.Builder builder = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + path))
                    .timeout(Duration.ofSeconds(20))
                    .header("Content-Type", "application/json");
            if (token != null) {
                builder.header("Authorization", token);
            }

            HttpRequest request = builder.method(method, HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("API request failed: " + method + " " + path, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("API request interrupted: " + method + " " + path, e);
        }
    }

    private static class LoginRequest {
        private final String email;
        private final String password;

        private LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    private static class LoginResponse {
        private String accessToken;

        private String getAccessToken() {
            return accessToken;
        }
    }
}
