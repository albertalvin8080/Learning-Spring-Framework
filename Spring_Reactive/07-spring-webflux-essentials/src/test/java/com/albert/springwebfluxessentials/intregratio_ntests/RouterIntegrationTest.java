package com.albert.springwebfluxessentials.intregratio_ntests;

import com.albert.springwebfluxessentials.model.Product;
import com.albert.springwebfluxessentials.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import static com.albert.springwebfluxessentials.util.TestProductGenerator.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@ActiveProfiles("test")
public class RouterIntegrationTest
{
    @Autowired
    private ProductRepository repository;

    private static final Product validProduct = getValidProduct();
    private static final Product productToBeSaved = getProductToBeSaved();
    private static final Product updatedProduct = getUpdatedProduct();

    @Test
    public void test() throws InterruptedException {
        final Mono<Product> saved = repository.save(productToBeSaved);
        repository.findAll().log().subscribe();
        Thread.sleep(3000);
    }
}
