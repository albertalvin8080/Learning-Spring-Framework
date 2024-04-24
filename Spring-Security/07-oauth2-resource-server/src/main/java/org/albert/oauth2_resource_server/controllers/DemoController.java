package org.albert.oauth2_resource_server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    @PreAuthorize("hasAuthority('write') or authentication.name.equals('frieren')")
    public ResponseEntity<String> demo(Authentication authentication) {
//        SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok("Hello from DemoController!");
    }

    @GetMapping("/clientTest")
    // name is the client id in this case (apparently)
    @PreAuthorize("authentication.name == 'client'")
    public ResponseEntity<String> clientTest(Authentication authentication) {
        return ResponseEntity.ok("Hello Client, It's DemoController!<br>" +
                ((Jwt) authentication.getPrincipal()).getClaims());
    }
}
