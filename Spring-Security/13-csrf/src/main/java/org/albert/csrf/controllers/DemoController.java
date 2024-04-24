package org.albert.csrf.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public String index() {
        return "index";
    }

    @PostMapping("/smth")
    public String smth() {
        System.out.println("Inside POST request");
        return "test";
    }
}
