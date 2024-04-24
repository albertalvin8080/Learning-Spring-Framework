package com.albert.springdatajdbc.service;

import com.albert.springdatajdbc.model.Product;
import com.albert.springdatajdbc.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public Iterable<Product> listAll() {
        return productRepository.findAll();
    }

    public Iterable<Product> findByName(String name) {
        return productRepository.findByName(name);
    }
}
