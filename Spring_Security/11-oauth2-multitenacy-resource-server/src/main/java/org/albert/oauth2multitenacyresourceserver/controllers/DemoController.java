package org.albert.oauth2multitenacyresourceserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public ResponseEntity<String> demo(Authentication authentication) {
        String iss = null;

        if(authentication instanceof JwtAuthenticationToken x) {
            iss = x.getToken().getClaim("iss");
        }
        else if(authentication instanceof BearerTokenAuthentication x) {
            iss = x.getTokenAttributes().get("iss").toString();
        }

        return ResponseEntity.ok("Multitenancy Resource Server.<br>Issuer: " + iss);
    }
}
