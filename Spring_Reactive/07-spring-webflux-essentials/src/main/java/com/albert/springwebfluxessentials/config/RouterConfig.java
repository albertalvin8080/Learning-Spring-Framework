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
     * You can simply declare a single 'ProductHandler' with the same method
     * signatures and use method reference to map the endpoints inside the Router.
     * */
//    private final FindAllHandler findAllHandler;
//    private final FindByIdHandler findByIdHandler;
//    private final SaveHandler saveHandler;
//    private final UpdateHandler updateHandler;
//    private final DeleteHandler deleteHandler;

    private final ProductHandler productHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.nest(
                RequestPredicates.path("/product"),
                RouterFunctions.route()
                        .GET("/all", productHandler::findAll)
                        .GET("/{id}", productHandler::findById)
                        .POST("/save", productHandler::save)
                        .POST("/save-all", productHandler::saveAll)
                        .PUT("/{id}", productHandler::update)
                        .DELETE("/{id}", productHandler::delete)
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
