package com.albert.learningmore.controller;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class TestController {

    @GetMapping(path = "/image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> image() throws IOException {
        final byte[] bytes = Files.readAllBytes(Path.of("./src/main/resources/images/img.png"));
        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }

    @GetMapping(path = "/header")
    public ResponseEntity<String> testingHeader(@RequestHeader(name = "HEADER-TEST") String header) {
        return new ResponseEntity<>(header, HttpStatus.OK);
    }
}
