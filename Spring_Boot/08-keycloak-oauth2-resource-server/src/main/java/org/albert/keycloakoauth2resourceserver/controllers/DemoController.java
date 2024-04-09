package org.albert.keycloakoauth2resourceserver.controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @PreAuthorize("hasRole('client_user')")
    @GetMapping("/data")
    public ResponseEntity<String> data() {
        return ResponseEntity.ok("Your key is: " + UUID.randomUUID().toString());
    }

    @PreAuthorize("hasRole('client_admin')")
    @PostMapping("/save")
    public ResponseEntity<String> data(@RequestBody String message) {
        return ResponseEntity.ok(message);
    }
}
