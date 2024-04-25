package com.albert.core.mappers;

import com.albert.core.dto.InventoryResponseDto;
import com.albert.core.models.inventory.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper
{
    public InventoryResponseDto from(Inventory inventory) {
        return InventoryResponseDto.builder()
                .skuCode(inventory.getSkuCode())
                .hasInStock(inventory.getQuantity() > 0)
                .build();
    }
}
