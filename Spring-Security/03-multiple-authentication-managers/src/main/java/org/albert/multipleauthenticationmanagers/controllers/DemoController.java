package org.albert.multipleauthenticationmanagers.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping
    public ResponseEntity<String> demo() {
        return new ResponseEntity<>("Hello, Multiple Managers!", HttpStatus.OK);
    }
}
