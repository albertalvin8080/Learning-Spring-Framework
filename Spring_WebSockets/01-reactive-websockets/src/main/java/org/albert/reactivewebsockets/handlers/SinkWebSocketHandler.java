package org.albert.reactivewebsockets.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class SinkWebSocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        final Flux<WebSocketMessage> flux = Flux
                .create(fluxSink -> {
                    for (int i = 0; i < 5; ++i) {
                        fluxSink.next(i);
                    }
                    fluxSink.complete();
                })
                .map(i -> session.textMessage(String.valueOf(i)))
                .delayElements(Duration.ofSeconds(1));

        return session.send(flux);
    }
}
