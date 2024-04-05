package org.albert.publishersubscribersubscription.config;

import lombok.RequiredArgsConstructor;
import org.albert.publishersubscribersubscription.handler.DemoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(DemoHandler handler) {
        return RouterFunctions
                .route()
                .GET("/test", handler::test)
                .build();
    }
}
