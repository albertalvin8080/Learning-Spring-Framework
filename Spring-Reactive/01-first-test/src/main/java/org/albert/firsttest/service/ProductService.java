package org.albert.firsttest.service;

import lombok.RequiredArgsConstructor;
import org.albert.firsttest.model.Product;
import org.albert.firsttest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Flux<Product> findAll() {
        return productRepository.findAll()
                .delayElements(Duration.ofSeconds(2));
    }
}
