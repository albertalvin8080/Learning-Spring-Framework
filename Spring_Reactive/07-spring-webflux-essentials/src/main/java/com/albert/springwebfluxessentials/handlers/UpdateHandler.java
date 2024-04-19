package com.albert.springwebfluxessentials.handlers;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import com.albert.springwebfluxessentials.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UpdateHandler implements HandlerFunction<ServerResponse>
{
    private final ProductValidator productValidator;
    private final ProductService productService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
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
}
