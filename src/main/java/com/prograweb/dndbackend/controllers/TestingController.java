package com.prograweb.dndbackend.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.prograweb.dndbackend.models.User;
import com.prograweb.dndbackend.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/testing")
public class TestingController {

    @Autowired
    private UserService userService;

    
    @GetMapping("/healthcheck")
    public String getMethodName() {
        return new String("Service is up and running!");
    }

    @PostMapping("/testUser")
    public ResponseEntity<List<User>> testUser() {
        userService.addUser(new User("mockUser", "mockEmail@example.com"));
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    

}
