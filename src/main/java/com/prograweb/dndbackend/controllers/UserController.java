package com.prograweb.dndbackend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.prograweb.dndbackend.domain.dtos.ApiResponse;
import com.prograweb.dndbackend.domain.dtos.RegisterUserDTO;
import com.prograweb.dndbackend.domain.models.User;
import com.prograweb.dndbackend.services.UserService;

import jakarta.validation.Valid;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@Valid @RequestBody RegisterUserDTO registerDTO) {
        try {
            User createdUser = userService.registerUser(registerDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("User registered successfully", createdUser));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}
