package com.albert.springwebfluxessentials.services;

import com.albert.springwebfluxessentials.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@ExtendWith(SpringExtension.class)
class ProductServiceTest
{
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        BDDMockito.when(productRepository.findAll())
                .thenReturn(Flux.just(getValidProduct()));
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(getValidProduct()));
    }

    @Test
    @DisplayName("findAll returns Flux<Product> when successful.")
    public void findAll_ReturnsFluxOfProducts_WhenSuccessful() {
        StepVerifier
                .create(productService.findAll())
                .expectSubscription()
                .expectNext(getValidProduct())
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono<Product> when successful.")
    public void findById_ReturnsMonoOfProduct_WhenSuccessful() {
        StepVerifier
                .create(productService.findById(1L))
                .expectSubscription()
                .expectNext(getValidProduct())
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono.error() of ResponseStatusException when no products are found.")
    public void findById_ReturnsMonoErrorOfResponseStatusException_WhenNoProductsAreFound() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier
                .create(productService.findById(2L))
                .expectSubscription()
                .expectErrorMatches(throwable -> {
                    return throwable instanceof ResponseStatusException x && x.getStatusCode() == HttpStatus.NOT_FOUND;
                })
//                .expectError(ResponseStatusException.class)
                .verify();
    }
}