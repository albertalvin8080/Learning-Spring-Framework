package com.albert.springwebfluxessentials.unit_tests.routers;

import com.albert.springwebfluxessentials.config.RouterConfig;
import com.albert.springwebfluxessentials.config.WebPropertiesConfig;
import com.albert.springwebfluxessentials.handlers.ProductHandler;
import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.services.ProductService;
import com.albert.springwebfluxessentials.validators.ProductValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@Import({
        RouterConfig.class,       // Effectively exposes the endpoints.
        ProductHandler.class,     // Needed by 'RouterConfig'
        ProductValidator.class,   // Needed by 'ProductHandler'. 'Validator' is provided by Spring.
        WebPropertiesConfig.class // Needed by 'GlobalErrorWebExceptionHandler'
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

    @BeforeEach
    public void setUp() {
        Mockito.when(productService.findAll())
                .thenReturn(Flux.just(validProduct));

        Mockito.when(productService.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Mono.just(validProduct));
    }

    @Test
    public void findAll_ReturnsFluxOfProduct_WhenSuccessful() {
        webTestClient.get()
                .uri("/product/all")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(validProduct);
    }

    @Test
    public void findById_ReturnsMonoOfProduct_WhenSuccessful() {
        webTestClient.get()
                .uri("/product/1")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1)
                .contains(validProduct);
    }
}
