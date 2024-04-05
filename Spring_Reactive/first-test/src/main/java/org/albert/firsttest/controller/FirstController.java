package org.albert.firsttest.controller;

import lombok.RequiredArgsConstructor;
import org.albert.firsttest.model.Product;
import org.albert.firsttest.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class FirstController {

    private final ProductService productService;

    @GetMapping(value = "/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> findAll() {
        return productService.findAll();
    }
}
