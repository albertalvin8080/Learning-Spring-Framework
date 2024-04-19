package com.albert.springwebfluxessentials.config;

import com.albert.springwebfluxessentials.handlers.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.*;

@Configuration
@RequiredArgsConstructor
public class RouterConfig
{
    /*
     * It doesn't seem wise to have this many beans inside the Spring Context.
     * You could simply inject the 'ProductService' and 'ProductValidator' here
     * and use lambdas to replace these handlers, or just declare functions with
     * the same signature in the current class.
     * */
    private final FindAllHandler findAllHandler;
    private final FindByIdHandler findByIdHandler;
    private final SaveHandler saveHandler;
    private final UpdateHandler updateHandler;
    private final DeleteHandler deleteHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.nest(
                RequestPredicates.path("/product"),
                RouterFunctions.route()
                        .GET("/all", findAllHandler)
                        .GET("/{id}", findByIdHandler)
                        .POST("/save", saveHandler)
                        .PUT("/{id}", updateHandler)
                        .DELETE("/{id}", deleteHandler)
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
