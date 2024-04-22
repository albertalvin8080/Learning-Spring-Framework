package com.albert.springwebfluxessentials.intregratio_ntests;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

/*
 * It's actually better not to drop the entire database for each test in this case, otherwise
 * it would lead to concurrency issues, like a test dropping the database which the previous
 * test was using.
 * */
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterIntegrationTest
{
    @Autowired
    private ProductRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    private static final Product validProduct = getValidProduct();
    private static final Product productToBeSaved = getProductToBeSaved();
    private static final Product updatedProduct = getUpdatedProduct();

    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
    }

    @Test
    @DisplayName("findAll returns Flux<Product> when successful.")
    public void findAll_ReturnsFluxOfProduct_WhenSuccessful() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        final FluxExchangeResult<Product> result = webTestClient.get()
                .uri("/product/all")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class);

        StepVerifier.create(result.getResponseBody())
                .expectSubscription()
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns Mono<Product> when successful.")
    public void findById_ReturnsMonoOfProduct_WhenSuccessful() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        final FluxExchangeResult<Product> result = webTestClient
                .get()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$..name", savedProduct.getName());
                .returnResult(Product.class);

        StepVerifier.create(result.getResponseBody())
                .expectSubscription()
                .expectNext(savedProduct)
                .verifyComplete();
    }

    @Test
    @DisplayName("findById returns HttpStatus.NOT_FOUND when no products were found.")
    public void findById_ReturnsHttpStatusNotFound_WhenNoProductsWereFound() {
        webTestClient
                .get()
                .uri("/product/{id}", 101)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404);
    }

    @Test
    @DisplayName("save returns Mono<Product> when successful.")
    public void save_ReturnsMonoOfProduct_WhenSuccessful() {
        final FluxExchangeResult<Product> result = webTestClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved))
                .exchange()
                .expectStatus().isCreated()
//                .expectBodyList(Product.class)
//                .hasSize(1);
                .returnResult(Product.class);

        StepVerifier.create(result.getResponseBody())
                .expectSubscription()
                .expectNextMatches(product -> product.getId() != null)
                .verifyComplete();
    }

    @Test
    @DisplayName("save returns HttpStatus.BAD_REQUEST when the product has no name.")
    public void save_ReturnsHttpStatusBadRequest_WhenProductHasNoName() {
        webTestClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved.withName("")))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(entityExchangeResult -> {
                    final String body = new String(entityExchangeResult.getResponseBodyContent());
                    log.info(body);
                });
    }

    @Test
    @DisplayName("save returns HttpStatus.BAD_REQUEST when the product has no price.")
    public void save_ReturnsHttpStatusBadRequest_WhenProductHasNoPrice() {
        webTestClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved.withPrice(null)))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(entityExchangeResult -> {
                    final String body = new String(entityExchangeResult.getResponseBodyContent());
                    log.info(body);
                });
    }

    @Test
    @DisplayName("saveAll returns Mono<List<Product>> when successful.")
    public void saveAll_ReturnsMonoOfListOfProduct_WhenSuccessful() {
        final FluxExchangeResult<List<Product>> result = webTestClient.post()
                .uri("/product/save-all")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved)))
                .exchange()
                .expectStatus().isCreated()
                .returnResult(new ParameterizedTypeReference<List<Product>>(){});

        // Extracting Flux<List<Product>> to Flux<Product>
        final Flux<Product> productFlux = result.getResponseBody().flatMap(Flux::fromIterable);

        StepVerifier.create(productFlux)
                .expectSubscription()
                .expectNextMatches(product -> product.getId() != null)
                .expectNextMatches(product -> product.getId() != null)
                .verifyComplete();
    }

    @Test
    @DisplayName("saveAll returns HttpStatus.BAD_REQUEST when one of the products is invalid.")
    public void saveAll_ReturnsHttpStatusBadRequest_WhenOneOfProductsIsInvalid() {
        webTestClient.post()
                .uri("/product/save-all")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved.withName(""))))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status").isEqualTo(400);
    }

    @Test
    @DisplayName("update returns HttpStatus.NOT_FOUND when successful.")
    public void update_ReturnsHttpStatusNoContent_WhenSuccessful() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedProduct))
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("update returns HttpStatus.BAD_REQUEST when the product has no name.")
    public void update_ReturnsHttpStatusBadRequest_WhenProductHasNoName() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.
                        fromValue(updatedProduct.withName(""))
                )
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("update returns HttpStatus.BAD_REQUEST when the product has no price.")
    public void update_ReturnsHttpStatusBadRequest_WhenProductHasNoPrice() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.
                        fromValue(updatedProduct.withPrice(null))
                )
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("update returns HttpStatus.NOT_FOUND when no products were found.")
    public void update_ReturnsHttpStatusNotFound_WhenProductNoProductsWereFound() {
        webTestClient.put()
                .uri("/product/{id}", 101)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.
                        fromValue(updatedProduct)
                )
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("delete returns HttpStatus.NO_CONTENT whe successful.")
    public void delete_ReturnsHttpStatusNoContent_WhenSuccessful() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.delete()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @DisplayName("delete returns HttpStatus.NOT_FOUND whe no products were found.")
    public void delete_ReturnsHttpStatusNotFound_WhenNoProductsWereFound() {
        webTestClient.delete()
                .uri("/product/{id}", 101)
                .exchange()
                .expectStatus().isNotFound();
    }
/**/
    // Prototype
//    @Test
//    @DisplayName("findAll returns Flux<Product> when successful.")
//    public void findAll_ReturnsFluxOfProduct_WhenSuccessful() {
//        final Product savedProduct = repository.save(productToBeSaved).block();
//
//        webTestClient.get()
//                .uri("/product/all")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBodyList(Product.class)
//                // With this is possible to see if the returned prices are different, like 400.00 and 400
//                // Also, the equals and hashcode for Product were changed to only include de id property.
//                .value(products -> {
//                    log.info(products.get(0).toString());
//                    log.info(savedProduct.toString());
//                })
//                .returnResult();
//    }
}
