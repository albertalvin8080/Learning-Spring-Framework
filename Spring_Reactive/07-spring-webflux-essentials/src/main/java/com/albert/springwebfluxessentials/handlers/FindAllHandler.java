package com.albert.springwebfluxessentials.handlers;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FindAllHandler implements HandlerFunction<ServerResponse>
{
    private final ProductService productService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findAll(), Product.class);
    }
}
