package org.albert.customauthentication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping
    public ResponseEntity<String> demo() {
        return new ResponseEntity<>("Authentication was successful!", HttpStatus.OK);
    }
}
