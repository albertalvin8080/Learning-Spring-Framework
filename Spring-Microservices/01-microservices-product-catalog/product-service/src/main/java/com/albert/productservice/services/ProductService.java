package com.albert.productservice.services;

import com.albert.productservice.dto.ProductRequest;
import com.albert.productservice.dto.ProductResponse;
import com.albert.productservice.mappers.ProductMapper;
import com.albert.productservice.model.Product;
import com.albert.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductMapper mapper = new ProductMapper();
    private final ProductRepository repository;

    public ProductResponse save(ProductRequest productRequest) {
        final Product saved = repository.save(mapper.from(productRequest));
        return mapper.from(saved);
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::from)
                .toList();
    }
}
