package com.albert.springwebfluxessentials.unit_tests;

import com.albert.springwebfluxessentials.config.RouterConfig;
import com.albert.springwebfluxessentials.config.WebPropertiesConfig;
import com.albert.springwebfluxessentials.error.CustomErrorAttributes;
import com.albert.springwebfluxessentials.handlers.ProductHandler;
import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import com.albert.springwebfluxessentials.validators.ProductValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.json.Jackson2CodecSupport;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2SmileEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ResponseStatusException;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import({
        RouterConfig.class,         // Effectively exposes the endpoints.
        ProductHandler.class,       // Needed by 'RouterConfig'.
        ProductValidator.class,     // Needed by 'ProductHandler'. 'Validator' is provided by Spring.
        WebPropertiesConfig.class,  // Needed by 'GlobalErrorWebExceptionHandler'.
        CustomErrorAttributes.class // Customizes the error response (not used).
})
public class RouterUnitTest
{
    @MockBean
    private ProductService productService;

    @Autowired
    private WebTestClient webTestClient;

    private static final Product validProduct = getValidProduct();
    private static final Product productToBeSaved = getProductToBeSaved();
    private static final Product updatedProduct = getUpdatedProduct();

    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        // findAll()
        Mockito.when(productService.findAll())
                .thenReturn(Flux.just(validProduct));
        // findById()
        Mockito.when(productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(validProduct));
        // save()
        Mockito.when(productService.save(productToBeSaved))
                .thenReturn(Mono.just(validProduct));
        // saveAll()
        Mockito.when(productService.saveAll(List.of(productToBeSaved, productToBeSaved)))
                        .thenReturn(Mono.just(List.of(validProduct, validProduct)));
        // update()
        Mockito.when(productService.update(validProduct))
                .thenReturn(Mono.empty());
        // delete()
        Mockito.when(productService.delete(validProduct.getId()))
                .thenReturn(Mono.empty());
    }

    @Test
    @DisplayName("findAll returns Flux<Product> when successful.")
    public void findAll_ReturnsFluxOfProduct_WhenSuccessful() {
        webTestClient.get()
                .uri("/product/all")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(validProduct);

        BDDMockito.verify(productService, BDDMockito.times(1))
                .findAll();
    }

    @Test
    @DisplayName("findById returns Mono<Product> when successful.")
    public void findById_ReturnsMonoOfProduct_WhenSuccessful() {
        webTestClient.get()
                .uri("/product/{id}", 1)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(validProduct);

        BDDMockito.verify(productService, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("findById returns HttpStatus.NOT_FOUND when no products were found.")
    public void findById_ReturnsHttpStatusNotFound_WhenNoProductsWereFound() {
        BDDMockito.given(productService.findById(ArgumentMatchers.anyLong()))
                .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No products were found."));

        webTestClient.get()
                .uri("/product/{id}", 1)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404);

        BDDMockito.verify(productService, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("save returns Mono<Product> when successful.")
    public void save_ReturnsMonoOfProduct_WhenSuccessful() {
        webTestClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo(productToBeSaved.getName())
                .jsonPath("$.price").isEqualTo(productToBeSaved.getPrice().doubleValue());

        BDDMockito.verify(productService, BDDMockito.times(1))
                .save(ArgumentMatchers.any(Product.class));
    }

    /* Needs the default Spring Beans for deserializing List<>. See the Integration Test. */
//    @Test
//    @DisplayName("saveAll returns Mono<List<Product>> when successful.")
//    public void saveAll_ReturnsMonoOfListOfProduct_WhenSuccessful() {
//        final FluxExchangeResult<List<Product>> result = webTestClient.post()
//                .uri("/product-save-all")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved)))
//                .exchange()
//                .returnResult(new ParameterizedTypeReference<List<Product>>(){});
//
//        final Flux<Product> flux = result.getResponseBody().flatMap(Flux::fromIterable);
//
//        StepVerifier.create(flux)
//                .expectSubscription()
//                .expectNextMatches(product -> product.getId() != null)
//                .expectNextMatches(product -> product.getId() != null)
//                .verifyComplete();
//    }

    @Test
    @DisplayName("update returns HttpStatus.NO_CONTENT when successful.")
    public void update_ReturnsHttpStatusNoContent_WhenSuccessful() {
        webTestClient.put()
                .uri("/product/{id}", validProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(validProduct))
                .exchange()
                .expectStatus().isNoContent();

        BDDMockito.verify(productService, BDDMockito.times(1))
                .update(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("update returns HttpStatus.NOT_FOUND when no products were found.")
    public void update_ReturnsHttpStatusNotFound_WhenNoProductsWereFound() {
        BDDMockito.given(productService.update(validProduct))
                        .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No products were found."));

        webTestClient.put()
                .uri("/product/{id}", validProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(validProduct))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404); // seems redundant

        BDDMockito.verify(productService, BDDMockito.times(1))
                .update(ArgumentMatchers.any(Product.class));
    }

    @Test
    @DisplayName("delete returns HttpStatus.NO_CONTENT when successful.")
    public void delete_ReturnsHttpStatusNoContent_WhenSuccessful() {
        webTestClient.delete()
                .uri("/product/{id}", validProduct.getId())
                .exchange()
                .expectStatus().isNoContent();

        BDDMockito.verify(productService, BDDMockito.times(1))
                .delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("delete returns HttpStatus.NOT_FOUND when no products were found.")
    public void delete_ReturnsHttpStatusNotFound_WhenNoProductsWereFound() {
        BDDMockito.given(productService.delete(ArgumentMatchers.anyLong()))
                        .willThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No products were found."));

        webTestClient.delete()
                .uri("/product/{id}", validProduct.getId())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404);

        BDDMockito.verify(productService, BDDMockito.times(1))
                .delete(ArgumentMatchers.anyLong());
    }
}
