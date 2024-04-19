package com.albert.springwebfluxessentials.error;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Component
@Order(-2) // Needed because 'DefaultErrorWebExceptionHandler' has @Order(-1)
public class GlobalErrorWebExceptionHandler extends AbstractErrorWebExceptionHandler
{
    public GlobalErrorWebExceptionHandler(
            ErrorAttributes errorAttributes,
            WebProperties.Resources resources,
            ApplicationContext applicationContext,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        super(errorAttributes, resources, applicationContext);
        setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(
                RequestPredicates.all(), // Routes to all endpoints.
                this::formatErrorResponse // Basically a handler.
        );
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        final ErrorAttributeOptions errorAttributeOptions = isTraceEnabled(request) ?
                ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE) :
                ErrorAttributeOptions.defaults();

        final Map<String, Object> errorAttributesMap = getErrorAttributes(request, errorAttributeOptions);
        final int status = (int) Optional.ofNullable(errorAttributesMap.get("status")).orElse(500);

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorAttributesMap));
    }
}
