package org.albert.securingspringreactive.config;

import lombok.RequiredArgsConstructor;
import org.albert.securingspringreactive.handlers.AuthInfoHandler;
import org.albert.securingspringreactive.handlers.DataHandler;
import org.albert.securingspringreactive.handlers.HelloHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {

    private final DataHandler dataHandler;
    private final AuthInfoHandler authInfoHandler;
    private final HelloHandler helloHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions
                .route()
                .path("/demo", builder -> builder
                        .GET("/data", dataHandler)
                        .GET("/auth", authInfoHandler)
                        .POST("/hello", helloHandler)
                )
                .build();
    }
}
