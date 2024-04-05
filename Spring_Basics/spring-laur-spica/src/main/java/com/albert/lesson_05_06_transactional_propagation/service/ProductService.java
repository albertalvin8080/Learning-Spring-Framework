package com.albert.lesson_05_06_transactional_propagation.service;

import com.albert.lesson_05_06_transactional_propagation.domain.Product;
import com.albert.lesson_05_06_transactional_propagation.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(Product product) {
        productRepository.save(product);
//        throw new Exception(":(");
        throw new RuntimeException(":(");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveTen() {
        for(int i = 0; i < 10; ++i) {
            productRepository.save(new Product("Madoka Novel", 99));
            if (i == 5) throw new RuntimeException(":(");
        }
    }
}
