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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterIntegrationTestV2
{
    @Autowired
    private ProductRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    private static final String ADMIN_USER = "albert";
    private static final String REGULAR_USER = "lucas";
    private static final String NONEXISTENT_USER = "lucas";

    private static final Product validProduct = getValidProduct();
    private static final Product productToBeSaved = getProductToBeSaved();
    private static final Product updatedProduct = getUpdatedProduct();

    @BeforeAll
    public static void setUpBlockHound() {
        BlockHound.install();
    }

    /*
     * In this version, @WithUserDetails() is used to provide a username to be searched in the database.
     * The provided name will be sent as a parameter to 'ReactiveUserDetailsService.findByUsername(...)',
     * so you don't need to provide the password, just the name.
     *
     * Beware: @WithMockUser() doesn't work in the same way. It puts an Authorization object in the
     * Security Context, so the database is not consulted. Because of this, Endpoint-Level Authorization
     * is not possible, after all, the Security Filter Chain was "skipped". Only Method-Level Authorization
     * is possible in this case.
     *
     * In summary:
     *  - @WithUserDetails() "skips" Authentication phase:
     *        Set up an Authentication object in the SecurityContext for the test.
     *        It does this by loading the UserDetails object returned by the
     *        UserDetailsService for the provided username.
     *
     *  - @WithMockUser() "skips" both Authentication and Endpoint-Level Authorization:
     *        Sets up a mock user in the SecurityContext with certain authorities
     *        for the test, which can then be used to test method-level security constraints.
     * */

    @Test
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
    @DisplayName("save returns Mono<Product> when user has role ADMIN.")
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
    @WithUserDetails(REGULAR_USER)
    @DisplayName("save returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void save_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() {
        webTestClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved))
                .exchange()
                .expectStatus().isForbidden();
    }

    /*
     * @WithUserDetails() skips the Authentication phase, so this case cannot be tested.
     * */
//    @Test
//    @WithUserDetails(NONEXISTENT_USER)
//    @DisplayName("save returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
//    public void save_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() {
//        webTestClient.post()
//                .uri("/product/save")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(productToBeSaved))
//                .exchange()
//                .expectStatus().isUnauthorized();
//    }

    @Test
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
    @DisplayName("saveAll returns Mono<List<Product>> when user has role ADMIN.")
    public void saveAll_ReturnsMonoOfListOfProduct_WhenSuccessful() {
        final FluxExchangeResult<List<Product>> result = webTestClient.post()
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
                .expectNextMatches(product -> product.getId() != null)
                .verifyComplete();
    }

    @Test
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(REGULAR_USER)
    @DisplayName("saveAll returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void saveAll_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() {
        webTestClient.post()
                .uri("/product/save-all")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved)))
                .exchange()
                .expectStatus().isForbidden();
    }

    /*
     * @WithUserDetails() skips the Authentication phase, so this case cannot be tested.
     * */
//    @Test
//    @WithUserDetails(NONEXISTENT_USER)
//    @DisplayName("saveAll returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
//    public void saveAll_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() {
//        webTestClient.post()
//                .uri("/product/save-all")
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(List.of(productToBeSaved, productToBeSaved)))
//                .exchange()
//                .expectStatus().isUnauthorized();
//    }

    @Test
    @WithUserDetails(ADMIN_USER)
    @DisplayName("update returns HttpStatus.NO_CONTENT when user has role ADMIN.")
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
    @WithUserDetails(REGULAR_USER)
    @DisplayName("update returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void update_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.put()
                .uri("/product/{id}", savedProduct.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedProduct))
                .exchange()
                .expectStatus().isForbidden();
    }

    /*
     * @WithUserDetails() skips the Authentication phase, so this case cannot be tested.
     * */
//    @Test
//    @WithUserDetails(NONEXISTENT_USER)
//    @DisplayName("update returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
//    public void update_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() {
//        final Product savedProduct = repository.save(productToBeSaved).block();
//
//        webTestClient.put()
//                .uri("/product/{id}", savedProduct.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(BodyInserters.fromValue(updatedProduct))
//                .exchange()
//                .expectStatus().isUnauthorized();
//    }

    @Test
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
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
    @WithUserDetails(ADMIN_USER)
    @DisplayName("delete returns HttpStatus.NO_CONTENT whe user has role ADMIN.")
    public void delete_ReturnsHttpStatusNoContent_WhenSuccessful() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.delete()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @WithUserDetails(REGULAR_USER)
    @DisplayName("delete returns HttpStatus.FORBIDDEN when user doesn't have right role.")
    public void delete_ReturnsHttpStatusForbidden_WhenUserDoesntHaveRightRole() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.delete()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus().isForbidden();
    }

    /*
     * @WithUserDetails() skips the Authentication phase, so this case cannot be tested.
     * */
//    @Test
//    @WithUserDetails(NONEXISTENT_USER)
//    @DisplayName("delete returns HttpStatus.UNAUTHORIZED when user doesn't exist.")
//    public void delete_ReturnsHttpStatusUnauthorized_WhenUserDoesntExist() {
//        final Product savedProduct = repository.save(productToBeSaved).block();
//
//        webTestClient.delete()
//                .uri("/product/{id}", savedProduct.getId())
//                .exchange()
//                .expectStatus().isUnauthorized();
//    }

    @Test
    @WithUserDetails(ADMIN_USER)
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
