package com.albert.orderservice.controllers;

import com.albert.orderservice.dto.OrderDto;
import com.albert.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController
{
    private final OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderDto orderDto) {
        return service.placeOrder(orderDto);
    }
}
