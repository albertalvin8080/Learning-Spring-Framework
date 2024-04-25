package com.albert.core.mappers;

import com.albert.core.dto.ProductRequestDto;
import com.albert.core.dto.ProductResponseDto;
import com.albert.core.models.product.Product;

public class ProductMapper
{
    public Product from(ProductRequestDto productRequestDto) {
        return Product.builder()
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .build();
    }

    public ProductResponseDto from(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
