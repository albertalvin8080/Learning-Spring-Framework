package org.albert.methodauthorization.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/demo")
public class DemoController {

    @PreAuthorize("hasAuthority('write')")
    @PostMapping("/demo1")
    public ResponseEntity<String> demo1() {
        return ResponseEntity.ok("demo 1");
    }

    @PreAuthorize("hasAuthority('read')")
    @GetMapping("/demo2")
    public String demo2() {
        return "demo 2";
    }

    @PreAuthorize("#username == authentication.name or hasAuthority('write')")
    @GetMapping("/demo3/{username}")
    public String demo3(@PathVariable String username) {
        return "demo 3: " + username;
    }

    // This is the syntax for using a Spring Bean to evaluate a condition.
    // It's recommended in case of a too big condition.
    @PreAuthorize("@demo4AuthorizationConditionEvaluator.usernameOrWriteAuthority(#username)")
    @GetMapping("/demo4/{username}")
    public String demo4(@PathVariable String username) {
        return "demo 4: " + username;
    }

    // WARNING: nerver use @PostAuthorize() with mutable http methods.
    // This 'returnObject' represents the returned object.
    @PostAuthorize("returnObject != 'demo 5' or not hasAuthority('write')")
    @PostMapping("/demo5")
    public String demo5() {
        log.info("inside demo5()");
        return "demo 5";
    }

    // This 'filterObject' represents each object within the request body List<>/array
    @PreFilter("filterObject.contains('a')")
    @GetMapping("/demo6")
    public List<String> demo6(@RequestBody List<String> list) {
        // ["Homura", "Fern", "Frieren", "Madoka"]
        return list;
    }

    // This 'filterObject' represents each object within returned List<>/array
    @PostFilter("not filterObject.contains('a')")
    @GetMapping("/demo7")
    public String[] demo7() {
        // WARNING: you MUST NOT return an immutable list, like List.of()
        return new String[]{"Homura", "Fern", "Frieren", "Madoka"};
    }
}
