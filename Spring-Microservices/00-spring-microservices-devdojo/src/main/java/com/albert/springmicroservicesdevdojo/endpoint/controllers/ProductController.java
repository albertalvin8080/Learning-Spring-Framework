package com.albert.springmicroservicesdevdojo.endpoint.controllers;

import com.albert.springmicroservicesdevdojo.endpoint.services.ProductService;
import com.albert.springmicroservicesdevdojo.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/v1/admin/product")
public class ProductController
{
    private final ProductService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Product>> findAllPageable(Pageable pageable) {
        return ResponseEntity.ok(service.findAllPageable(pageable));
    }

}
