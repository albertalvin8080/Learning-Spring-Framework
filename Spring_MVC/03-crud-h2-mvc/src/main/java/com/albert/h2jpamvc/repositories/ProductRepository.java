package com.albert.h2jpamvc.repositories;

import com.albert.h2jpamvc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
