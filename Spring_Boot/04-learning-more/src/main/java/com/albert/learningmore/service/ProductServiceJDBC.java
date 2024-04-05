package com.albert.learningmore.service;

import com.albert.learningmore.entity.Product;
import com.albert.learningmore.repository.ProductRepositoryJDBC;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductServiceJDBC {
    private ProductRepositoryJDBC productRepositoryJDBC;

    public void save(Product product) {
        productRepositoryJDBC.save(product);
    }
}
