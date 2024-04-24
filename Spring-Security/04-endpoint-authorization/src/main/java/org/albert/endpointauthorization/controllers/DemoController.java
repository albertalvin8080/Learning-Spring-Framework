package org.albert.endpointauthorization.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/demo1")
    public ResponseEntity<String> demo() {
        return new ResponseEntity<>("demo 1", HttpStatus.OK);
    }

    @GetMapping("/demo2")
    public ResponseEntity<String> demo2() {
        return new ResponseEntity<>("demo 2", HttpStatus.OK);
    }

    @PostMapping("/demo3")
    public ResponseEntity<String> demo3() {
        return new ResponseEntity<>("demo 3", HttpStatus.OK);
    }
}
