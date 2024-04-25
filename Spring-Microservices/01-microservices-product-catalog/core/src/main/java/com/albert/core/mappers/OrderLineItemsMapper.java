package com.albert.core.mappers;

import com.albert.core.dto.OrderLineItemsDto;
import com.albert.core.models.order.OrderLineItems;

public class OrderLineItemsMapper
{
    public OrderLineItems from(OrderLineItemsDto dto) {
        return OrderLineItems.builder()
                .skuCode(dto.getSkuCode())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }
}
