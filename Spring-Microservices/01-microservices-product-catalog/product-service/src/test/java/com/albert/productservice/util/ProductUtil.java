package com.albert.productservice.util;

import com.albert.core.dto.ProductRequestDto;
import com.albert.core.models.product.Product;

import java.math.BigDecimal;

public class ProductUtil
{
    public static ProductRequestDto getProductRequest() {
        return ProductRequestDto.builder()
                .name("Dark Souls 1")
                .price(BigDecimal.valueOf(100.00))
                .build();
    }

    public static Product getProductToSave() {
        return Product.builder()
                .name("Dark Souls 1")
                .price(BigDecimal.valueOf(100.00))
                .build();
    }
}
