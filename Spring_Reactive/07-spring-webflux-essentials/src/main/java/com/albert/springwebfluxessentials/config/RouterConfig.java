package com.albert.springwebfluxessentials.config;

import com.albert.springwebfluxessentials.handlers.FindByIdHandler;
import lombok.RequiredArgsConstructor;
import com.albert.springwebfluxessentials.handlers.FindAllHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class RouterConfig {
    private final FindAllHandler findAllHandler;
    private final FindByIdHandler findByIdHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.nest(
                RequestPredicates.path("/product"),
                RouterFunctions.route()
                        .GET("/all", findAllHandler)
                        .GET("/{id}", findByIdHandler)
                        .build()
        );
    }

//    @Bean
//    public RouterFunction<ServerResponse> routerFunction() {
//        return RouterFunctions.route()
//                .GET("/product/all", findAllHandler)
//                .GET("/product/{id}", findByIdHandler)
//                .build();
//    }
}
