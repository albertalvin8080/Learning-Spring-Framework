package org.example.springwebfluxessentials.controllers;

import lombok.RequiredArgsConstructor;
import org.example.springwebfluxessentials.model.Product;
import org.example.springwebfluxessentials.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public Flux<Product> findAll() {
        return productService.findAll();
    }
}
