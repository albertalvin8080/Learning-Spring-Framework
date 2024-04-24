package org.albert.oauth2multitenancyclient.controller;

import lombok.RequiredArgsConstructor;
import org.albert.oauth2multitenancyclient.services.ResourceServerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client") // http://localhost:3333/client
public class ClientController {

    private final ResourceServerService service;

    @GetMapping("/jwt")
    public ResponseEntity<String> getDataUsingAuthorizationServer1() {
        return ResponseEntity.ok(service.getDataUsingAuthorizationServer1());
    }

    @GetMapping("/opaque")
    public ResponseEntity<String> getDataUsingAuthorizationServer2() {
        return ResponseEntity.ok(service.getDataUsingAuthorizationServer2());
    }
}
