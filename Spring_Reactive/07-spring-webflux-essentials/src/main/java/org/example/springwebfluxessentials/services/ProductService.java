package org.example.springwebfluxessentials.services;

import lombok.RequiredArgsConstructor;
import org.example.springwebfluxessentials.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
