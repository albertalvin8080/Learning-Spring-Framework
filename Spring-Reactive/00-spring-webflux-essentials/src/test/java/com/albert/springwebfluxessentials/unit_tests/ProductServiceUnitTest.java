package com.albert.springwebfluxessentials.unit_tests;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.repositories.ProductRepository;
import com.albert.springwebfluxessentials.services.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@ExtendWith(SpringExtension.class)
class ProductServiceUnitTest
{
    @InjectMocks
    private ProductService productService;
    @Mock
    private ProductRepository productRepository;

    final static Product productToBeSaved = getProductToBeSaved();
    final static Product validProduct = getValidProduct();
    final static Product updatedProduct = getUpdatedProduct();
    
    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        // findAll()
        BDDMockito.when(productRepository.findAll())
                .thenReturn(Flux.just(validProduct));

        // findById()
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(validProduct));

        // save()
        BDDMockito.when(productRepository.save(productToBeSaved))
                .thenReturn(Mono.just(validProduct));

        // saveAll()
        BDDMockito.when(productRepository.saveAll(List.of(productToBeSaved, productToBeSaved)))
                .thenReturn(Flux.just(validProduct, validProduct));

        // update()
        BDDMockito.when(productRepository.save(validProduct))
                .thenReturn(Mono.just(updatedProduct));

        // delete()
        BDDMockito.when(productRepository.delete(validProduct))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findAll returns Flux<Product> when successful.")
    public void findAll_ReturnsFluxOfProducts_WhenSuccessful() {
        StepVerifier
                .create(productService.findAll())
                .expectSubscription()
                .expectNext(validProduct)
                .verifyComplete();

        BDDMockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("findById returns Mono<Product> when successful.")
    public void findById_ReturnsMonoOfProduct_WhenSuccessful() {
        StepVerifier
                .create(productService.findById(1L))
                .expectSubscription()
                .expectNext(validProduct)
                .verifyComplete();

        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
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

        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("save returns Mono<Product> when successful.")
    public void save_ReturnsMonoOfProduct_WhenSuccessful() {
        StepVerifier
                .create(productService.save(productToBeSaved))
                .expectSubscription()
                .expectNext(validProduct)
                .verifyComplete();

        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .save(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("saveAll returns Flux<Product> when successful.")
    public void saveAll_ReturnsFluxOfProduct_WhenSuccessful() {
        final Flux<Product> flux = productRepository.saveAll(List.of(productToBeSaved, productToBeSaved));

        StepVerifier.create(flux)
                .expectSubscription()
                .expectNext(validProduct, validProduct)
                .verifyComplete();
    }

    @Test
    @DisplayName("update returns Mono<Void> when successful.")
    public void update_ReturnsMonoOfVoid_WhenSuccessful() {
        StepVerifier
                .create(productService.update(validProduct))
                .expectSubscription()
                .verifyComplete();

        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .save(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("update returns Mono.error() of ResponseStatusException when no products are found.")
    public void update_ReturnsMonoErrorOfResponseStatusException_WhenNoProductsAreFound() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                        .thenReturn(Mono.empty());

        StepVerifier
                .create(productService.update(validProduct))
                .expectSubscription()
                .expectErrorMatches(throwable ->
                    throwable instanceof ResponseStatusException x && x.getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();

        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("delete returns Mono<Void> when successful.")
    public void delete_ReturnsMonoOfVoid_WhenSuccessful() {
        StepVerifier
                .create(productService.delete(validProduct.getId()))
                .expectSubscription()
                .verifyComplete();

        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .delete(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("delete returns Mono.error() of ResponseStatusException when no products are found.")
    public void delete_ReturnsMonoErrorOfResponseStatusException_WhenNoProductsAreFound() {
        BDDMockito.when(productRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.empty());

        StepVerifier
                .create(productService.delete(validProduct.getId()))
                .expectSubscription()
                .expectErrorMatches(throwable ->
                        throwable instanceof ResponseStatusException x && x.getStatusCode() == HttpStatus.NOT_FOUND)
                .verify();

        BDDMockito.verify(productRepository, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
    }
}