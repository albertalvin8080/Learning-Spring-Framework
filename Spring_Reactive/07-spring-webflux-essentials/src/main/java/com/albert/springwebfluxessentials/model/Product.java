package com.albert.springwebfluxessentials.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@With
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product", schema = "products")
public class Product {
    @Id
    private Long id;
    @NotEmpty(message = "Product name must not be null nor empty.")
    private String name;
//    @NotEmpty(message = "Product price must not be empty.")
    private BigDecimal price;
}
