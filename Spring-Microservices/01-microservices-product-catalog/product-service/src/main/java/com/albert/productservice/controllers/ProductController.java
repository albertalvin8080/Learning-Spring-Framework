package com.albert.productservice.controllers;

import com.albert.core.dto.ProductRequestDto;
import com.albert.core.dto.ProductResponseDto;
import com.albert.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController
{
    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDto save(@RequestBody ProductRequestDto productRequestDto) {
        return service.save(productRequestDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponseDto> findAll() {
        return service.findAll();
    }
}
