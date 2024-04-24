package org.albert.reactivewebsockets.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;

@RestController
@RequiredArgsConstructor
public class MessagesController {
    // in a real world app, this must be inside the Service class.
    private final Sinks.Many<String> many;

    @PostMapping("/message")
    public void message(@RequestBody String message) {
        many.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
