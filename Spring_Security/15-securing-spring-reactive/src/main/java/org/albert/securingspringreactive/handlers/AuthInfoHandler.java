package org.albert.securingspringreactive.handlers;

import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthInfoHandler implements HandlerFunction<ServerResponse> {

    @Override
    public @NonNull Mono<ServerResponse> handle(@NonNull ServerRequest request) {
        final Mono<Object> mono = ReactiveSecurityContextHolder
                .getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName);
                // used for debugging
//                .map(a -> {
//                    System.out.println(a.getDetails());
//                    return a.getDetails();
//                });

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(mono, Object.class);
    }
}
