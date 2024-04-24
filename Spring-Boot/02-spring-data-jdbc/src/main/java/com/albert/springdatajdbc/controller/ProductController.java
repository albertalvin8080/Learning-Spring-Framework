package com.albert.springdatajdbc.controller;

import com.albert.springdatajdbc.model.Product;
import com.albert.springdatajdbc.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Iterable<Product>> listAll() {
        return new ResponseEntity<>(productService.listAll(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("testing", HttpStatus.OK);
    }
}
