package com.albert.productservice.util;

import com.albert.productservice.dto.ProductRequest;
import com.albert.productservice.model.Product;

import java.math.BigDecimal;

public class ProductUtil
{
    public static ProductRequest getProductRequest() {
        return ProductRequest.builder()
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
