package org.albert.reactivewebsockets.handlers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class CustomWebSocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        final Flux<WebSocketMessage> map = Flux.just("A", "B", "C", "D")
                .map(str -> session.textMessage(str));

        /*
        * The concatWith() operator in Project Reactor concatenates the emissions of
        * multiple Publishers (like Flux or Mono) by subscribing to them one at a time,
        * only moving to the next Publisher when the current one completes.
        *
        * In your code, session.receive() is a Flux that represents an infinite stream
        * of incoming WebSocket messages. This Flux will not complete unless the session
        * is closed. Therefore, concatWith(map) will not subscribe to the map Flux until
        * session.receive() completes, which means the messages “A”, “B”, “C”, “D” will
        * not be sent until the session is closed.
        * */
        // this doesn't send the letters.
//        final Flux<WebSocketMessage> map2 = session.receive()
//                .map(WebSocketMessage::getPayloadAsText)
//                .map(str -> session.textMessage(str))
//                .concatWith(map);

        // this sends the letters only once because the events are all consumed.
//        final Flux<WebSocketMessage> map2 = map.concatWith(
//                session.receive()
//                        .map(WebSocketMessage::getPayloadAsText)
//                        .map(session::textMessage)
//        );

        final Flux<WebSocketMessage> map3 = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .map(session::textMessage);

        return session.send(map3);
    }
}
