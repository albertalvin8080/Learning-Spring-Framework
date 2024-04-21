package com.albert.springwebfluxessentials.util;

import com.albert.springwebfluxessentials.model.Product;

import java.math.BigDecimal;

public class TestProductGenerator
{
    public static Product getValidProduct() {
        return Product.builder()
                .id(100L)
                .name("Television")
                .price(BigDecimal.valueOf(400.00))
                .build();
    }

    public static Product getProductToBeSaved() {
        return Product.builder()
                .name("Television")
                .price(BigDecimal.valueOf(400.00))
                .build();
    }

    public static Product getUpdatedProduct() {
        return Product.builder()
                .id(100L)
                .name("Inflationary Television")
                .price(BigDecimal.valueOf(550.00))
                .build();
    }
}
