package com.albert.springwebfluxessentials.handlers;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import com.albert.springwebfluxessentials.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler
{
    private final ProductValidator productValidator;
    private final ProductService productService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findAll(), Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findById(id), Product.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(productValidator::validate)
                .flatMap(productService::save)
                .flatMap(product ->
                        ServerResponse.status(HttpStatus.CREATED)
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(Mono.just(product), Product.class)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(Product.class)
                .map(product -> product.withId(id))
                .flatMap(productValidator::validate)
                .flatMap(productService::update)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .build()
                );
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));
        return productService.delete(id)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .build()
                );
    }
}
