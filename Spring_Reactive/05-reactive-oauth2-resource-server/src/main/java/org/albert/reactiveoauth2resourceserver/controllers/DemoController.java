package org.albert.reactiveoauth2resourceserver.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/data")
    public ResponseEntity<String> data() {
        return ResponseEntity.ok("01000001");
    }
}
