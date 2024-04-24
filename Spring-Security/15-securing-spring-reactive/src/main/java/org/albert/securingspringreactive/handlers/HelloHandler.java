package org.albert.securingspringreactive.handlers;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class HelloHandler implements HandlerFunction<ServerResponse> {
    @Override
    public @NonNull Mono<ServerResponse> handle(@NonNull ServerRequest request) {
        final Flux<String> just = Flux
                .just("Hello", "My", "Friend")
                .delayElements(Duration.ofSeconds(1));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(just, String.class);
    }
}
