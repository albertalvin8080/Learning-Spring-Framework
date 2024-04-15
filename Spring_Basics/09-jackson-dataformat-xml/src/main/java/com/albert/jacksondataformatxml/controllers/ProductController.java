package com.albert.jacksondataformatxml.controllers;

import com.albert.jacksondataformatxml.entities.Product;
import com.albert.jacksondataformatxml.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    /*
    * Header: {
    *   <KEY>   <VALUE>
    *   Accept  application/xml
    * }
    * */

    private final ProductService productService;

    @GetMapping(value = "/all", produces = "application/xml")
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping(path = "/save", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Void> save(@RequestBody List<Product> productList) {
        log.info(productList.toString());
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
