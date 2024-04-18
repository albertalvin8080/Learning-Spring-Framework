package org.example.springwebfluxessentials.services;

import lombok.RequiredArgsConstructor;
import org.example.springwebfluxessentials.model.Product;
import org.example.springwebfluxessentials.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }
}
