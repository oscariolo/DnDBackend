package com.prograweb.dndbackend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.util.Base64;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Value("${keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final ObjectMapper objectMapper = new ObjectMapper();
    public AuthTokenResponse authenticate(String username, String password) {
        try {
            String tokenUrl = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";
            String body = "grant_type=password" +
                    "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                    "&username=" + URLEncoder.encode(username, StandardCharsets.UTF_8) +
                    "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8) +
                    "&scope=openid%20profile%20email";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(tokenUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode jsonNode = objectMapper.readTree(response.body());
                return new AuthTokenResponse(
                        jsonNode.get("access_token").asText(),
                        jsonNode.get("refresh_token").asText(),
                        jsonNode.get("expires_in").asLong()
                );
            } else {
                throw new RuntimeException("Authentication failed: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error authenticating with Keycloak: " + e.getMessage(), e);
        }
    }

    public boolean validateTokenWithKeycloak(String token) {
        try {
            String tokenIntrospectUrl = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token/introspect";
            
            String body = "token=" + URLEncoder.encode(token, StandardCharsets.UTF_8) +
                    "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8);
            
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(tokenIntrospectUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            int status = response.statusCode();
            String respBody = response.body();
            if (status == 200) {
                JsonNode jsonNode = objectMapper.readTree(respBody);
                boolean active = jsonNode.get("active").asBoolean(false);
                logger.debug("Keycloak introspect response active=" + active + " for token");
                return active;
            } else {
                logger.warn("Keycloak introspection returned status " + status + " with body: " + respBody);
            }
            return false;
        } catch (Exception e) {
            logger.error("Error validating token with Keycloak: " + e.getMessage(), e);
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            // Decode JWT without validation (validation is done via Keycloak)
            String[] parts = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JsonNode jsonNode = objectMapper.readTree(payload);
            return jsonNode.get("preferred_username").asText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getIssuerFromToken(String token) {
        try {
            String[] parts = token.split("\\.");
            String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
            JsonNode jsonNode = objectMapper.readTree(payload);
            return jsonNode.has("iss") ? jsonNode.get("iss").asText() : null;
        } catch (Exception e) {
            return null;
        }
    }

    public AuthTokenResponse refreshToken(String refreshToken) {
        try {
            String tokenUrl = keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

            String body = "grant_type=refresh_token" +
                    "&client_id=" + URLEncoder.encode(clientId, StandardCharsets.UTF_8) +
                    "&client_secret=" + URLEncoder.encode(clientSecret, StandardCharsets.UTF_8) +
                    "&refresh_token=" + URLEncoder.encode(refreshToken, StandardCharsets.UTF_8);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(tokenUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonNode jsonNode = objectMapper.readTree(response.body());
                return new AuthTokenResponse(
                        jsonNode.get("access_token").asText(),
                        jsonNode.get("refresh_token").asText(),
                        jsonNode.get("expires_in").asLong()
                );
            } else {
                throw new RuntimeException("Token refresh failed");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error refreshing token: " + e.getMessage(), e);
        }
    }
    public static class AuthTokenResponse {
        public String accessToken;
        public String refreshToken;
        public Long expiresIn;

        public AuthTokenResponse(String accessToken, String refreshToken, Long expiresIn) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresIn = expiresIn;
        }
    }
}
