package com.albert.productservice.services;

import com.albert.core.dto.ProductRequestDto;
import com.albert.core.dto.ProductResponseDto;
import com.albert.core.mappers.ProductMapper;
import com.albert.core.models.product.Product;
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

    public ProductResponseDto save(ProductRequestDto productRequestDto) {
        final Product saved = repository.save(mapper.from(productRequestDto));
        return mapper.from(saved);
    }

    public List<ProductResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::from)
                .toList();
    }
}
