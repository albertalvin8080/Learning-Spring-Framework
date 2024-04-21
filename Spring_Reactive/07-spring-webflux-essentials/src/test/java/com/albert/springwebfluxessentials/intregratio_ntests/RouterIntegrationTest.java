package com.albert.springwebfluxessentials.intregratio_ntests;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RouterIntegrationTest
{
    @Autowired
    private ProductRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    private static final Product validProduct = getValidProduct();
    private static final Product productToBeSaved = getProductToBeSaved();
    private static final Product updatedProduct = getUpdatedProduct();

    @Test
    @DisplayName("findAll returns Flux<Product> when successful.")
    public void findAll_ReturnsFluxOfProduct_WhenSuccessful() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient.get()
                .uri("/product/all")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                // With this is possible to see if the returned prices are different, like 400.00 and 400
                // Also, the equals and hashcode for Product were changed to only include de id property.
                .value(products -> {
                    log.info(products.get(0).toString());
                    log.info(savedProduct.toString());
                })
                .contains(savedProduct);
    }

    @Test
    @DisplayName("findById returns Mono<Product> when successful.")
    public void findById_ReturnsMonoOfProduct_WhenSuccessful() {
        final Product savedProduct = repository.save(productToBeSaved).block();

        webTestClient
                .get()
                .uri("/product/{id}", savedProduct.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$..name", savedProduct.getName());
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
        webTestClient.post()
                .uri("/product/save")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(productToBeSaved))
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(Product.class)
                .hasSize(1);
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

    
}
