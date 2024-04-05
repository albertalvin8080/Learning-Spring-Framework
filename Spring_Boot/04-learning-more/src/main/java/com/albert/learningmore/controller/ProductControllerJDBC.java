package com.albert.learningmore.controller;

import com.albert.learningmore.entity.Product;
import com.albert.learningmore.service.ProductServiceJDBC;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/productJDBC")
@AllArgsConstructor
public class ProductControllerJDBC {
    private ProductServiceJDBC productServiceJDBC;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Product product) {
        productServiceJDBC.save(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
