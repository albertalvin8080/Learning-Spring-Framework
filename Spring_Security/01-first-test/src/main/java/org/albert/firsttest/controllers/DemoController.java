package org.albert.firsttest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

@RestController
@RequiredArgsConstructor
public class DemoController {

    private final PasswordEncoder passwordEncoder;

    @GetMapping(path = "/hello")
    public ResponseEntity<String> hello() {
//        System.out.println(passwordEncoder.encode("12345"));

        var a = SecurityContextHolder.getContext().getAuthentication();
        a.getAuthorities().forEach(System.out::println);

        return new ResponseEntity<>("Hello, Security!", HttpStatus.OK);
    }
}
