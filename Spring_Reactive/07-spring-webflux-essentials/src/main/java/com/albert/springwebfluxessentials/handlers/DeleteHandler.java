package com.albert.springwebfluxessentials.handlers;

import com.albert.springwebfluxessentials.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class DeleteHandler implements HandlerFunction<ServerResponse>
{
    private final ProductService productService;

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));
        return productService.delete(id)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .build()
                );
    }
}
