package com.albert.springwebfluxessentials.handlers;

import com.albert.springwebfluxessentials.model.Product;
import lombok.RequiredArgsConstructor;
import com.albert.springwebfluxessentials.services.ProductService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FindByIdHandler implements HandlerFunction<ServerResponse>
{
    private final ProductService productService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findById(id), Product.class);
    }
}
