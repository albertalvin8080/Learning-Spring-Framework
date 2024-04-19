package com.albert.springwebfluxessentials.handlers;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import com.albert.springwebfluxessentials.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SaveHandler implements HandlerFunction<ServerResponse>
{
    private final ProductValidator productValidator;
    private final ProductService productService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(productValidator::validate)
                .flatMap(productService::save)
                .flatMap(product ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(product), Product.class)
                );
    }
}
