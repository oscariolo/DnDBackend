package com.prograweb.dndbackend.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class TestingController {
    
    @GetMapping("/healthcheck")
    public String getMethodName(@RequestParam String param) {
        return new String("Service is up and running! You sent: " + param);
    }
    

}
