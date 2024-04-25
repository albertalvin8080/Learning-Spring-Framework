package com.albert.core.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductResponseDto
{
    private String id;
    private String name;
    private BigDecimal price;
}
