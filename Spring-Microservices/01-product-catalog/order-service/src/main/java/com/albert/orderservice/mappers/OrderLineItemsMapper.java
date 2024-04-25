package com.albert.orderservice.mappers;

import com.albert.orderservice.dto.OrderLineItemsDto;
import com.albert.orderservice.model.OrderLineItems;

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
