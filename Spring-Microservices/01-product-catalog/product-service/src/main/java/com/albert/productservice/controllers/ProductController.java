package com.albert.productservice.controllers;

import com.albert.productservice.dto.ProductRequest;
import com.albert.productservice.dto.ProductResponse;
import com.albert.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/app/product")
public class ProductController
{
    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse save(@RequestBody ProductRequest productRequest) {
        return service.save(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAll() {
        return service.findAll();
    }
}
