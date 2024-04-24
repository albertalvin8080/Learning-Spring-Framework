package org.albert.endpointauthorization.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/test1")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("test 1", HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2() {
        return new ResponseEntity<>("test 2", HttpStatus.OK);
    }
}
