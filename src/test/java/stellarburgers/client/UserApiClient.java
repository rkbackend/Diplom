package stellarburgers.client;

import stellarburgers.model.UserCredentials;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserApiClient {
    private static final String API_URL = "https://stellarburgers.education-services.ru/api";
    private static final Pattern ACCESS_TOKEN_PATTERN = Pattern.compile("\"accessToken\"\\s*:\\s*\"([^\"]+)\"");
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public void createUser(UserCredentials user) {
        sendJson("/auth/register", "POST", userJson(user), null);
    }

    public Optional<String> login(UserCredentials user) {
        HttpResponse<String> response = sendJson("/auth/login", "POST",
                "{\"email\":\"" + escape(user.getEmail()) + "\",\"password\":\"" + escape(user.getPassword()) + "\"}",
                null);
        return extractAccessToken(response.body());
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

    private Optional<String> extractAccessToken(String body) {
        Matcher matcher = ACCESS_TOKEN_PATTERN.matcher(body);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    private String userJson(UserCredentials user) {
        return "{\"email\":\"" + escape(user.getEmail()) + "\","
                + "\"password\":\"" + escape(user.getPassword()) + "\","
                + "\"name\":\"" + escape(user.getName()) + "\"}";
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
