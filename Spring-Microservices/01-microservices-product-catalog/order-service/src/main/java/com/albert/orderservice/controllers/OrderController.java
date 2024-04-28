package com.albert.orderservice.controllers;

import com.albert.core.dto.OrderDto;
import com.albert.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController
{
    private final OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFailureFallback")
//    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderDto orderDto) {
        return CompletableFuture.supplyAsync(() -> service.placeOrder(orderDto));
    }

    private CompletableFuture<String> inventoryFailureFallback(OrderDto orderDto, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oh no! Something bad happened.");
    }
}
