package org.albert.securingspringreactive.handlers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class CsrfTokenTestingHandler implements HandlerFunction<ServerResponse> {
    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        String key = CsrfToken.class.getName();
        Mono<CsrfToken> csrfToken = exchange.getAttribute(key) != null ? exchange.getAttribute(key) : Mono.empty();

        return csrfToken.doOnSuccess(token -> {
            // Perform operations with the token here
        });

        return null;
    }
}
