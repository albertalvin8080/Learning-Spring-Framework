package org.albert.routerfunction.services;

import org.albert.routerfunction.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.stream.Stream;

@Service
public class ProductService {

    public Flux<Product> getAll() {
        return Flux.fromStream(Stream.of(
            Product.builder().id(1L).name("TV").price(BigDecimal.valueOf(100.12)).build(),
            Product.builder().id(3L).price(BigDecimal.valueOf(1000.00)).build(),
            Product.builder().id(2L).name("Phone").price(BigDecimal.valueOf(400.99)).build()
        )).delayElements(Duration.ofMillis(2000));
    }
}
