package org.albert.testsunitintregration.controller;

import lombok.RequiredArgsConstructor;
import org.albert.testsunitintregration.model.Product;
import org.albert.testsunitintregration.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/")
    public List<Product> findAll() {
        return productService.findAll();
    }
}
