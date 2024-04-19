package com.albert.springwebfluxessentials.services;

import com.albert.springwebfluxessentials.model.Product;
import lombok.RequiredArgsConstructor;
import com.albert.springwebfluxessentials.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    public Mono<Product> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(generateNotFoundStatusException());
    }

    private <T> Mono<T> generateNotFoundStatusException() {
        return Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found.")
        );
    }

    public Mono<Product> save(Product product) {
        return productRepository.save(product);
    }

    public Mono<Void> update(Product product) {
        return findById(product.getId()) // Checks if really exists.
                .then(Mono.just(product))
                .flatMap(productRepository::save)
                .then();
    }

    public Mono<Void> delete(Long id) {
        return findById(id)
                .flatMap(productRepository::delete);
    }
}
