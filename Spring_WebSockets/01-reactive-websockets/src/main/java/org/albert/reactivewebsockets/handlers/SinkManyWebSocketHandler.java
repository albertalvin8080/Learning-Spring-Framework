package org.albert.reactivewebsockets.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class SinkManyWebSocketHandler implements WebSocketHandler {

    private final Sinks.Many<String> many;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        /*
        * takes messages sent (through POST method) to @MessagesController
        * */
        final Flux<WebSocketMessage> flux = many.asFlux()
                .map(session::textMessage)
                .delayElements(Duration.ofMillis(500));

        return session.send(flux);
    }
}
