package com.albert.springwebfluxessentials.unit_tests.services;

import com.albert.springwebfluxessentials.repositories.ProductRepository;
import com.albert.springwebfluxessentials.services.ProductService;
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

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@ExtendWith(SpringExtension.class)
class ProductServiceUnitTest
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
        // findAll()
        BDDMockito.when(productRepository.findAll())
                .thenReturn(Flux.just(getValidProduct()));

        // findById()
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(getValidProduct()));

        // save()
        BDDMockito.when(productRepository.save(getProductToBeSaved()))
                .thenReturn(Mono.just(getValidProduct()));

        // update()
        BDDMockito.when(productRepository.save(getValidProduct()))
                .thenReturn(Mono.just(getUpdatedProduct()));

        // delete()
        BDDMockito.when(productRepository.delete(getValidProduct()))
                .thenReturn(Mono.empty());
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

    @Test
    @DisplayName("save returns Mono<Product> when successful.")
    public void save_ReturnsMonoOfProduct_WhenSuccessful() {
        StepVerifier
                .create(productService.save(getProductToBeSaved()))
                .expectSubscription()
                .expectNext(getValidProduct())
                .verifyComplete();
    }

    @Test
    @DisplayName("update returns Mono<Void> when successful.")
    public void update_ReturnsMonoOfVoid_WhenSuccessful() {
        StepVerifier
                .create(productService.update(getValidProduct()))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("update returns Mono.error() of ResponseStatusException when no products are found.")
    public void update_ReturnsMonoErrorOfResponseStatusException_WhenNoProductsAreFound() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Mono.empty());

        StepVerifier
                .create(productService.update(getValidProduct()))
                .expectSubscription()
                .expectErrorMatches(throwable ->
                    throwable instanceof ResponseStatusException x && x.getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();
    }

    @Test
    @DisplayName("delete returns Mono<Void> when successful.")
    public void delete_ReturnsMonoOfVoid_WhenSuccessful() {
        StepVerifier
                .create(productService.delete(getValidProduct().getId()))
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    @DisplayName("delete returns Mono.error() of ResponseStatusException when no products are found.")
    public void delete_ReturnsMonoErrorOfResponseStatusException_WhenNoProductsAreFound() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier
                .create(productService.delete(getValidProduct().getId()))
                .expectSubscription()
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException x && x.getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();
    }
}