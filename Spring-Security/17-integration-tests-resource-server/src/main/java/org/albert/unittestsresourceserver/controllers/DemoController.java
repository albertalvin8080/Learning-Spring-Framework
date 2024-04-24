package org.albert.unittestsresourceserver.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/data")
    public ResponseEntity<String> data() {
        return ResponseEntity.ok("Data: 01000001");
    }

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/save")
    public ResponseEntity<Void> save(@RequestBody String data) {
        log.info(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
