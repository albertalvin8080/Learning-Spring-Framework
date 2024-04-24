package com.albert.springwebfluxessentials.handlers;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import com.albert.springwebfluxessentials.validators.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

/*
 * When you are testing the swagger-ui.html, beware of the Session Cookie. Otherwise, you'll be like
 * "oh no it's not asking for authentication", but in reality, you technically have already been authenticated.
 * */
@Component
@RequiredArgsConstructor
public class ProductHandler
{
    private final ProductValidator productValidator;
    private final ProductService productService;

    @Operation(
            tags = "Searching",
            summary = "Finds all products in database.",
            security = @SecurityRequirement(name = "BasicAuthenticationScheme")
    )
    @PreAuthorize("hasRole('USER')")
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findAll(), Product.class);
    }

    @Operation(
            tags = "Searching",
            summary = "Finds a specific product based on it's id.",
            security = @SecurityRequirement(name = "BasicAuthenticationScheme"),
            parameters = @Parameter(
                    name = "id",
                    required = true,
                    in = ParameterIn.PATH,
                    description = "ID of the product to search for.",
                    schema = @Schema(type = "integer", format = "int64") // int64 -> long
            )
    )
    public Mono<ServerResponse> findById(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(productService.findById(id), Product.class);
    }

    @Operation(
            tags = "Saving",
            summary = "Saves a new product into the database.",
            security = @SecurityRequirement(name = "BasicAuthenticationScheme"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)
                    ))
    )
    public Mono<ServerResponse> save(ServerRequest request) {
        return request.bodyToMono(Product.class)
                .flatMap(productValidator::validate)
                .flatMap(productService::save)
                .flatMap(product -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(product), Product.class)
                );
    }

    @Operation(
            tags = "Saving",
            summary = "Saves a list of new products into the database.",
            security = @SecurityRequirement(name = "BasicAuthenticationScheme"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Product.class))
                    ))
    )
    public Mono<ServerResponse> saveAll(ServerRequest request) {
        return request.bodyToMono(new ParameterizedTypeReference<List<Product>>(){})
                .flatMap(productValidator::validateMany)
                .flatMap(productService::saveAll)
                .flatMap(products -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.TEXT_EVENT_STREAM)
                        .body(BodyInserters.fromValue(products))
                );
    }

    @Operation(
            tags = "Updating",
            summary = "Updates an existing product whiting the database.",
            security = @SecurityRequirement(name = "BasicAuthenticationScheme"),
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Product.class)
                    )),
            parameters = @Parameter(
                    name = "id",
                    required = true,
                    in = ParameterIn.PATH,
                    description = "ID of the product to be updated.",
                    schema = @Schema(type = "integer", format = "int64")
            )
    )
    public Mono<ServerResponse> update(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));

        return request.bodyToMono(Product.class)
                .map(product -> product.withId(id))
                .flatMap(productValidator::validate)
                .flatMap(productService::update)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .build()
                );
    }

    @Operation(
            tags = "Deleting",
            summary = "Deletes an existing product whiting the database.",
            security = @SecurityRequirement(name = "BasicAuthenticationScheme"),
            parameters = @Parameter(
                    name = "id",
                    required = true,
                    in = ParameterIn.PATH,
                    description = "ID of the product to be deleted.",
                    schema = @Schema(type = "integer", format = "int64")
            )
    )
    public Mono<ServerResponse> delete(ServerRequest request) {
        final Long id = Long.valueOf(request.pathVariable("id"));
        return productService.delete(id)
                .then(ServerResponse
                        .status(HttpStatus.NO_CONTENT)
                        .build()
                );
    }
}
