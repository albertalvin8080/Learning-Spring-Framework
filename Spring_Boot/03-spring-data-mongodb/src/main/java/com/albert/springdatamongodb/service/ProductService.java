package com.albert.springdatamongodb.service;

import com.albert.springdatamongodb.model.Product;
import com.albert.springdatamongodb.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductRepository productRepository;

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

//    public List<Product> findByName(String name) {
//        return productRepository.findByName(name);
//    }
}
