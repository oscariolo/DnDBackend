package com.prograweb.dndbackend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class TestingController {
    
    @GetMapping("/healthcheck")
    public String getMethodName() {
        return new String("Service is up and running!");
    }
    

}
