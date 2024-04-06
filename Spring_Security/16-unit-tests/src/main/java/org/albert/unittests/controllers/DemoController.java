package org.albert.unittests.controllers;

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
        return ResponseEntity.ok("This is the data.");
    }

    @PostMapping("/save")
    public ResponseEntity<Void> saveData(@RequestBody String data) {
        log.info(data);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('write')")
    @PutMapping("/update")
    public ResponseEntity<Void> updateData(@RequestBody String data) {
        log.info(data);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
