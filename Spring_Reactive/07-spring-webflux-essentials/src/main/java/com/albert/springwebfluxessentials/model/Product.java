package com.albert.springwebfluxessentials.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;

@With
@Data
@Builder
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"name", "price"})
@AllArgsConstructor
@Table(name = "product", schema = "products")
public class Product {
    @Id
    private Long id;
    @NotEmpty(message = "Product name must not be null nor empty.")
    private String name;
    @NotNull(message = "Product price must not be null.")
    private BigDecimal price;
}
