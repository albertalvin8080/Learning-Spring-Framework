package com.albert.springwebfluxessentials.intregratio_ntests;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.repositories.ProductRepository;
import com.albert.springwebfluxessentials.util.WebTestClientUserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Class used for trying to resolve the problem where one test interferes with the other. Still no success.
 * */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RouterIntegrationTestV3
{
    @Autowired
    private ProductRepository repository;

    @Autowired
    private WebTestClientUserBuilder webTestClientUserBuilder;

    private WebTestClient regularUserClient;
    private WebTestClient adminUserClient;
    private WebTestClient unauthorizedClient;

    private static final Product validProduct = getValidProduct();
    private static final Product productToBeSaved = getProductToBeSaved();
    private static final Product updatedProduct = getUpdatedProduct();

    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
    }

    @BeforeEach
    public void setUp() {
        regularUserClient =
                webTestClientUserBuilder.getWebTestClient("lucas", "1234");
        adminUserClient =
                webTestClientUserBuilder.getWebTestClient("albert", "1234");
        unauthorizedClient =
                webTestClientUserBuilder.getWebTestClient("x", "x");
    }

    @Test
    @DisplayName("findAll returns Flux<Product> when successful.")
    public void findAll_ReturnsFluxOfProduct_WhenSuccessful() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        final FluxExchangeResult<Product> result = adminUserClient.get()
                .uri("/product/all")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Product.class);

        StepVerifier.create(result.getResponseBody())
                .expectSubscription()
                .expectNextMatches(product -> {
                    assertEquals(savedProduct, product);
                    latch.countDown();
                    return true;
                })
                .verifyComplete();

        latch.await();
    }

    @Test
    @DisplayName("findById returns Mono<Product> when successful.")
    public void findById_ReturnsMonoOfProduct_WhenSuccessful() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        final FluxExchangeResult<Product> result = adminUserClient
                .get()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$..name", savedProduct.getName());
                .returnResult(Product.class);

        StepVerifier.create(result.getResponseBody())
                .expectSubscription()
                .expectNextMatches(product -> {
                    assertEquals(savedProduct, product);
                    latch.countDown();
                    return true;
                })
                .verifyComplete();

        latch.await();
    }

    @Test
    @DisplayName("findById returns HttpStatus.NOT_FOUND when no products were found.")
    public void findById_ReturnsHttpStatusNotFound_WhenNoProductsWereFound() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient
                .get()
                .uri("/product/{id}", 101)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status")
                .value(status -> {
                    assertEquals(404, status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("save returns Mono<Product> when user has role ADMIN.")
    public void save_ReturnsMonoOfProduct_WhenSuccessful() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        final FluxExchangeResult<Product> result = adminUserClient.post()
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
                .expectNextMatches(product -> {
                    latch.countDown();
                    return product.getId() != null;
                })
                .verifyComplete();

        latch.await();
    }

    @Test
    @DisplayName("save returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void save_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        regularUserClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved))
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.FORBIDDEN.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("save returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
    public void save_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        unauthorizedClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved))
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("save returns HttpStatus.BAD_REQUEST when the product has no name.")
    public void save_ReturnsHttpStatusBadRequest_WhenProductHasNoName() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved.withName("")))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(entityExchangeResult -> {
                    final String body = new String(entityExchangeResult.getResponseBodyContent());
                    log.info(body);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("save returns HttpStatus.BAD_REQUEST when the product has no price.")
    public void save_ReturnsHttpStatusBadRequest_WhenProductHasNoPrice() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved.withPrice(null)))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .consumeWith(entityExchangeResult -> {
                    final String body = new String(entityExchangeResult.getResponseBodyContent());
                    log.info(body);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("saveAll returns Mono<List<Product>> when user has role ADMIN.")
    public void saveAll_ReturnsMonoOfListOfProduct_WhenSuccessful() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        final FluxExchangeResult<List<Product>> result = adminUserClient.post()
                .uri("/product/save-all")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved)))
                .exchange()
                .expectStatus().isCreated()
                .returnResult(new ParameterizedTypeReference<List<Product>>()
                {
                });

        // Extracting Flux<List<Product>> to Flux<Product>
        final Flux<Product> productFlux = result.getResponseBody().flatMap(Flux::fromIterable);

        StepVerifier.create(productFlux)
                .expectSubscription()
                .expectNextMatches(product -> product.getId() != null)
                .expectNextMatches(product -> {
                    latch.countDown();
                    return product.getId() != null;
                })
                .verifyComplete();

        latch.await();
    }

    @Test
    @DisplayName("saveAll returns HttpStatus.BAD_REQUEST when one of the products is invalid.")
    public void saveAll_ReturnsHttpStatusBadRequest_WhenOneOfProductsIsInvalid() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.post()
                .uri("/product/save-all")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved.withName(""))))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.status")
                .value(status -> {
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("saveAll returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void saveAll_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        regularUserClient.post()
                .uri("/product/save-all")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved)))
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.FORBIDDEN.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("saveAll returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
    public void saveAll_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        unauthorizedClient.post()
                .uri("/product/save-all")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved)))
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("update returns HttpStatus.NO_CONTENT when user has role ADMIN.")
    public void update_ReturnsHttpStatusNoContent_WhenSuccessful() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedProduct))
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.NO_CONTENT.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("update returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void update_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        regularUserClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedProduct))
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.FORBIDDEN.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("update returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
    public void update_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        unauthorizedClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedProduct))
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("update returns HttpStatus.BAD_REQUEST when the product has no name.")
    public void update_ReturnsHttpStatusBadRequest_WhenProductHasNoName() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.
                        fromValue(updatedProduct.withName(""))
                )
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("update returns HttpStatus.BAD_REQUEST when the product has no price.")
    public void update_ReturnsHttpStatusBadRequest_WhenProductHasNoPrice() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.
                        fromValue(updatedProduct.withPrice(null))
                )
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.BAD_REQUEST.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("update returns HttpStatus.NOT_FOUND when no products were found.")
    public void update_ReturnsHttpStatusNotFound_WhenProductNoProductsWereFound() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.put()
                .uri("/product/{id}", 101)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.
                        fromValue(updatedProduct)
                )
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.NOT_FOUND.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("delete returns HttpStatus.NO_CONTENT whe user has role ADMIN.")
    public void delete_ReturnsHttpStatusNoContent_WhenSuccessful() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.delete()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.NO_CONTENT.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("delete returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void delete_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        regularUserClient.delete()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.FORBIDDEN.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("delete returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
    public void delete_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() throws InterruptedException {
        final Product savedProduct = repository.save(productToBeSaved).block();
        CountDownLatch latch = new CountDownLatch(1);

        unauthorizedClient.delete()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.UNAUTHORIZED.value(), status);
                    latch.countDown();
                });

        latch.await();
    }

    @Test
    @DisplayName("delete returns HttpStatus.NOT_FOUND whe no products were found.")
    public void delete_ReturnsHttpStatusNotFound_WhenNoProductsWereFound() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        adminUserClient.delete()
                .uri("/product/{id}", 101)
                .exchange()
                .expectStatus()
                .value(status -> {
                    assertEquals(HttpStatus.NOT_FOUND.value(), status);
                    latch.countDown();
                });

        latch.await();
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
