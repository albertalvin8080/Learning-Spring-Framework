package com.albert.core.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductRequestDto
{
    private String name;
    private BigDecimal price;
}