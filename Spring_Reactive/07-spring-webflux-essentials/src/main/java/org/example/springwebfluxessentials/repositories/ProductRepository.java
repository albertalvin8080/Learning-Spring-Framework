package org.example.springwebfluxessentials.repositories;

import org.example.springwebfluxessentials.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
}
