package com.albert.springdatajdbc.repository;

import com.albert.springdatajdbc.model.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query("""
            SELECT * FROM product WHERE name = :name
            """)
    Iterable<Product> findByName(String name);
}
