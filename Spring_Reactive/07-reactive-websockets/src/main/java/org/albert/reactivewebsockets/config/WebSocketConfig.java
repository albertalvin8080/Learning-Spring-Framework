package org.albert.reactivewebsockets.config;

import lombok.RequiredArgsConstructor;
import org.albert.reactivewebsockets.handlers.CustomWebSocketHandler;
import org.albert.reactivewebsockets.handlers.SinkManyWebSocketHandler;
import org.albert.reactivewebsockets.handlers.SinkWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class WebSocketConfig {

    private final CustomWebSocketHandler customWebSocketHandler;
    private final SinkWebSocketHandler sinkWebSocketHandler;
    /*
    * Causes circular dependency if injected here. You may choose between injecting it
    * as a method parameter or instantiating SinkManyWebSocketHandler directly inside
    * the method (which is the used option)
    * */
//    private final SinkManyWebSocketHandler sinkManyWebSocketHandler;

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        return new SimpleUrlHandlerMapping(Map.of(
                "/ws/messages1", customWebSocketHandler,
                "/ws/messages2", sinkWebSocketHandler,
                "/ws/messages3", new SinkManyWebSocketHandler(sinkMany())
        ), 1);
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

    @Bean
    public Sinks.Many<String> sinkMany() {
        return Sinks.many()
                .multicast()         // to everyone who tries to connect,
                .directBestEffort(); // send each event immediately when available.
    }

}
