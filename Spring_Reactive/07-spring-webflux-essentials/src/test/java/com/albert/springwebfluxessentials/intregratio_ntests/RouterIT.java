package com.albert.springwebfluxessentials.intregratio_ntests;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.getProductToBeSaved;
import static com.albert.springwebfluxessentials.util.TestProductGenerator.getValidProduct;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RouterIT
{
    @MockBean // Spring annotation. It's different from @Mock
    private ProductService productService;

    @Autowired
    private WebTestClient webTestClient;

    private final Product validProduct = getValidProduct();
    private final Product productToBeSaved = getProductToBeSaved();

    @BeforeEach
    public void setUp() {
        Mockito.when(productService.findAll())
                .thenReturn(Flux.just(validProduct));

        Mockito.when(productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(validProduct));
    }

    @Test
    @DisplayName("findAllHandler returns Flux<Product> when successful.")
    public void findAllHandler_ReturnsFluxOfProduct_WhenSuccessful() {
        webTestClient
                .get()
                .uri("/product/all")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(validProduct);
    }

    @Test
    @DisplayName("findByIdHandler returns Mono<Product> when successful.")
    public void findByIdHandler_ReturnsMonoOfProduct_WhenSuccessful() {
        webTestClient.get()
                .uri("/product/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(validProduct);
    }
}
