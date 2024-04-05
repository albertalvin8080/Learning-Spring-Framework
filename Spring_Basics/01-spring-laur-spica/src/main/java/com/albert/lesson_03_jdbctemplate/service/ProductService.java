package com.albert.lesson_03_jdbctemplate.service;

import com.albert.lesson_03_jdbctemplate.domain.Product;
import com.albert.lesson_03_jdbctemplate.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final IRepository<Product> repository;

    @Autowired
    public ProductService(@Qualifier("prV2") IRepository<Product> repository) {
        this.repository = repository;
    }

    public void save(Product product) {
        repository.save(product);
    }

    public List<Product> listAll() {
        return repository.listAll();
    }
}
