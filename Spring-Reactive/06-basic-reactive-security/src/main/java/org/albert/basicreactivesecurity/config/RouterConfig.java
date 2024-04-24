package org.albert.basicreactivesecurity.config;

import lombok.RequiredArgsConstructor;
import org.albert.basicreactivesecurity.handlers.DataHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final DataHandler dataHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions
                .route()
                .POST("/demo/data", dataHandler)
                .build();
    }
}
