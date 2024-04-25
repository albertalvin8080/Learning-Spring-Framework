package com.albert.core.dto;

import lombok.*;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDto
{
    private String skuCode;
    private boolean hasInStock;
}
