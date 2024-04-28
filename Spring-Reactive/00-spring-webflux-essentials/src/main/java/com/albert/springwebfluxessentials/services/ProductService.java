package com.albert.springwebfluxessentials.services;

import com.albert.springwebfluxessentials.model.Product;
import lombok.RequiredArgsConstructor;
import com.albert.springwebfluxessentials.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Product> findById(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(generateNotFoundStatusException());
    }

    // The Generic is in the return type, thus I don't need to specify it at the method call.
    // See your notebook for more info.
    private <T> Mono<T> generateNotFoundStatusException() {
        return Mono.error(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found.")
        );
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Product> save(Product product) {
        log.info("UNDEAD id: {}", product.getId());
        return productRepository.save(product.withId(null));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<List<Product>> saveAll(List<Product> products) {
        products = products.stream().map(p -> p.withId(null)).collect(Collectors.toList());
        return productRepository.saveAll(products)
                .collectList();
        /*
         * this is the code used to force the commit when you don't have a @Transactional.
         * It's necessary to block the operation so r2dbc have time to commit it.
         */
//        /*return*/ productRepository.saveAll(products).collectList().block();
//        throw new RuntimeException();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> update(Product product) {
        log.info("UNDEAD id: {}", product.getId());
        return findById(product.getId())  // Checks if really exists.
                .then(Mono.just(product)) // Ignores the existing product.
                .flatMap(productRepository::save)
                .then();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Mono<Void> delete(Long id) {
        log.info("UNDEAD id: {}", id);
        return findById(id)
                .flatMap(productRepository::delete);
    }
}
