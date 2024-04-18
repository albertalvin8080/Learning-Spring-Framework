package com.albert.springwebfluxessentials.repositories;

import com.albert.springwebfluxessentials.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
