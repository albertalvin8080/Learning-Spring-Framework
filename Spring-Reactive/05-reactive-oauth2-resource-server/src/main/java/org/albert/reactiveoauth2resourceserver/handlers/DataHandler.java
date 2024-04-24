package org.albert.reactiveoauth2resourceserver.handlers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class DataHandler implements HandlerFunction<ServerResponse> {
    @Override
    public Mono<ServerResponse> handle(ServerRequest request) {
        /*
        * The ReactiveSecurityContextHolder.getContext() method is asynchronous and
        * returns a Mono<SecurityContext>. The doOnNext() and subscribe() methods
        * are also non-blocking and will execute in a different thread.
        *
        * However, the System.out::println is a blocking call, and it’s not guaranteed
        * to execute before the method handle returns. This is because in reactive
        * programming, operations are non-blocking and the execution model is based
        * on event-driven programming.
        * */
        ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .doOnNext(System.out::println)
                .subscribe(System.out::println);

        final Flux<String> just = Flux.just("01000001", "01000010", "01000011")
                .delayElements(Duration.ofSeconds(1));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(just, String.class);
    }
}
