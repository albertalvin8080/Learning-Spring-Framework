package com.albert.h2jpamvc.services;

import com.albert.h2jpamvc.entities.Product;
import com.albert.h2jpamvc.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(int id) {
        productRepository.deleteById(id);
    }

    public Product findById(int id) {
        return productRepository.findById(id).get();
    }
}
