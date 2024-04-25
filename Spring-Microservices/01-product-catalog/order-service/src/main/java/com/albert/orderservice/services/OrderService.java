package com.albert.orderservice.services;

import com.albert.orderservice.dto.OrderDto;
import com.albert.orderservice.mappers.OrderMapper;
import com.albert.orderservice.model.Order;
import com.albert.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService
{
    private final OrderRepository repository;
    private final OrderMapper orderMapper;

    public String placeOrder(OrderDto dto) {
        final Order order = orderMapper.from(dto);
        repository.save(order);

        return "Order %s placed successfully.".formatted(order.getOrderNumber());
    }
}
