package com.albert.orderservice.controllers;

import com.albert.core.dto.OrderDto;
import com.albert.orderservice.services.OrderService;

import brave.Span;
import brave.Tracer;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final Tracer tracer;
    private final OrderService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "inventoryFailureFallback")
    // @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderDto orderDto) {
        return CompletableFuture.supplyAsync(() -> {
            // Used for joining the span of the current request with the response of this CompletableFuture<>.
            Span currentSpan = tracer.currentSpan();
            Span childSpan = tracer.newChild(currentSpan.context()).name("placeOrder");

            try(Tracer.SpanInScope ws = tracer.withSpanInScope(childSpan.start())) {
                return service.placeOrder(orderDto);
            } finally {
                childSpan.finish();
            }
        });
    }

    private CompletableFuture<String> inventoryFailureFallback(OrderDto orderDto, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oh no! Something bad happened.");
    }
}
