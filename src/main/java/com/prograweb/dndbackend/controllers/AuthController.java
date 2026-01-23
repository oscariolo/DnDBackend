package com.prograweb.dndbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prograweb.dndbackend.domain.dtos.ApiResponse;
import com.prograweb.dndbackend.domain.dtos.LoginResponseDTO;
import com.prograweb.dndbackend.domain.dtos.LoginUserDTO;
import com.prograweb.dndbackend.domain.models.User;
import com.prograweb.dndbackend.services.AuthService;
import com.prograweb.dndbackend.services.AuthService.AuthTokenResponse;
import com.prograweb.dndbackend.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> authToken(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid Authorization header");
        }

        // Extract token from "Bearer <token>"
        String token = authHeader.substring(7);

        boolean isValid = authService.validateTokenWithKeycloak(token);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
        return ResponseEntity.ok(token);
    }
        

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@Valid @RequestBody LoginUserDTO loginDTO) {
        try {
            // Authenticate with Keycloak
            AuthTokenResponse tokens = authService.authenticate(
                    loginDTO.getEmailOrUsername(),
                    loginDTO.getPassword()
            );

            // Get user from database (email or username)
            User user = userService.getUserByEmailOrUsername(loginDTO.getEmailOrUsername());

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("User not found"));
            }

            // Build response
            LoginResponseDTO response = new LoginResponseDTO(
                    tokens.accessToken,
                    tokens.refreshToken,
                    user.getUsername(),
                    user.getEmail(),
                    tokens.expiresIn
            );

            return ResponseEntity.ok(ApiResponse.success("Login successful", response));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid credentials: " + e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            AuthTokenResponse tokens = authService.refreshToken(request.getRefreshToken());

            LoginResponseDTO response = new LoginResponseDTO(
                    tokens.accessToken,
                    tokens.refreshToken,
                    null,
                    null,
                    tokens.expiresIn
            );

            return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Token refresh failed"));
        }
    }

    // Inner class for refresh request
    public static class RefreshTokenRequest {
        public String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
