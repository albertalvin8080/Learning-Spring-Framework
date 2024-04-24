package com.albert.springmicroservicesdevdojo.endpoint.services;

import com.albert.springmicroservicesdevdojo.model.Product;
import com.albert.springmicroservicesdevdojo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService
{
    private final ProductRepository repository;

    public Page<Product> findAllPageable (Pageable pageable) {
        return repository.findAll(pageable);
    }
}
