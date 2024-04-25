package com.albert.orderservice.mappers;

import com.albert.orderservice.dto.OrderDto;
import com.albert.orderservice.model.Order;
import com.albert.orderservice.model.OrderLineItems;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderMapper
{
    private final OrderLineItemsMapper orderLineItemsMapper =
            new OrderLineItemsMapper();

    public Order from(OrderDto dto) {
        final List<OrderLineItems> itemsList = dto.getOrderLineItemsDtoList()
                .stream()
                .map(orderLineItemsMapper::from)
                .toList();

        return Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(itemsList)
                .build();
    }
}
