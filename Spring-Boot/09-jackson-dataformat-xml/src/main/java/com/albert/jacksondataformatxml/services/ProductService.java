package com.albert.jacksondataformatxml.services;

import com.albert.jacksondataformatxml.entities.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    public List<Product> findAll() {
        return List.of(
                Product.builder().id(1).name("RTX-9000").price(BigDecimal.valueOf(33)).build(),
                Product.builder().id(2).name("TV").price(BigDecimal.valueOf(444)).build(),
                Product.builder().id(3).name("Air").price(BigDecimal.valueOf(2222)).build(),
                Product.builder().id(4).name("Nothing").price(BigDecimal.valueOf(900)).build(),
                Product.builder().id(5).name("Bruh").price(BigDecimal.valueOf(1221)).build()
        );
    }

}