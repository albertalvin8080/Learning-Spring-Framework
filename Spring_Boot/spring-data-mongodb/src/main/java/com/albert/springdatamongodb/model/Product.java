package com.albert.springdatamongodb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private BigDecimal price;
}
