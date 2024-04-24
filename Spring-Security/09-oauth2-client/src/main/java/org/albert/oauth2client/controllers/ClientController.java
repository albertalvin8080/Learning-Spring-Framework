package org.albert.oauth2client.controllers;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2client.services.ResourceServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ResourceServerService service;

    // http://localhost:7070/client
    @GetMapping
    public ResponseEntity<String> getToken() {
        return ResponseEntity.ok(service.getData());
    }

}
