package org.albert.testsunitintregration.service;

import lombok.RequiredArgsConstructor;
import org.albert.testsunitintregration.model.Product;
import org.albert.testsunitintregration.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<String> findNamesWithEvenLength() {
        productRepository.voidMethodExample();
        return productRepository.findAllNames().stream()
                .filter(name -> name.length() % 2 == 0)
                .collect(Collectors.toList());
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
