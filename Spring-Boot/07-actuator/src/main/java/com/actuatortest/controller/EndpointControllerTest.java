package com.actuatortest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndpointControllerTest {

    @Autowired
    private HealthEndpoint healthEndpoint;

    @GetMapping("/ect")
    public String getHealth() {
        return healthEndpoint.health().toString();
    }
}
