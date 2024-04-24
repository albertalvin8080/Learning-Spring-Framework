package org.albert.basicreactivesecurity.handlers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
public class DataHandler implements HandlerFunction<ServerResponse> {

    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        final Flux<String> just = Flux
                .just("01000001")
                .concatWith(Flux.just("01000010"));
//                .delayElements(Duration.ofSeconds(1));

        Flux<String> requestBodyFlux = request.bodyToFlux(String.class)
//                .doOnNext(System.out::println)
                .concatWith(just);

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(requestBodyFlux, String.class);
    }
}
