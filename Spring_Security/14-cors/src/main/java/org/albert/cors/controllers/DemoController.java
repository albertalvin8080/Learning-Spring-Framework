package org.albert.cors.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @ResponseBody
//    @CrossOrigin("http://localhost:8080") // not used in production
    @GetMapping("/data")
    public ResponseEntity<String> data() {
        return ResponseEntity.ok("Data from demo controller");
    }
}
