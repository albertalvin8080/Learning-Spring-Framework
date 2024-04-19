package com.albert.springwebfluxessentials.validators;

import com.albert.springwebfluxessentials.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductValidator
{
    private final Validator validator;

    public Mono<Product> validate(Product product) {
        Errors errors = new BeanPropertyBindingResult(product, "product");
        validator.validate(product, errors);

        if(errors.hasErrors()) {
            final String defaultMessages = errors
                    .getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Validation error: " + defaultMessages)
            );
        }

        return Mono.just(product);
    }
}
