package com.albert.productservice.mappers;

import com.albert.productservice.dto.ProductRequest;
import com.albert.productservice.dto.ProductResponse;
import com.albert.productservice.model.Product;

public class ProductMapper
{
    public Product from(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();
    }

    public ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
