package com.albert.springwebfluxessentials.config;

import com.albert.springwebfluxessentials.handlers.*;
import com.albert.springwebfluxessentials.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.*;

/*
 * Documentation for integration with swagger-ui:
 * https://springdoc.org/#spring-webfluxwebmvc-fn-with-functional-endpoints
 * */
@Configuration
@RequiredArgsConstructor
@SecurityScheme(
        name = "BasicAuthenticationScheme",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
public class RouterConfig
{
    private final ProductHandler productHandler;

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/product/all", beanMethod = "findAll", beanClass = ProductHandler.class, method = RequestMethod.GET),
            @RouterOperation(path = "/product/{id}", beanMethod = "findById", beanClass = ProductHandler.class, method = RequestMethod.GET),
            @RouterOperation(path = "/product/save", beanMethod = "save", beanClass = ProductHandler.class, method = RequestMethod.POST),
            @RouterOperation(path = "/product/save-all", beanMethod = "saveAll", beanClass = ProductHandler.class, method = RequestMethod.POST),
            @RouterOperation(path = "/product/{id}", beanMethod = "update", beanClass = ProductHandler.class, method = RequestMethod.PUT),
            @RouterOperation(path = "/product/{id}", beanMethod = "delete", beanClass = ProductHandler.class, method = RequestMethod.DELETE)
    })
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

//    @Bean
//    public RouterFunction<ServerResponse> routerFunction() {
//        return RouterFunctions.route()
//                .GET("/product/all", findAllHandler)
//                .GET("/product/{id}", findByIdHandler)
//                .build();
//    }
}
