package org.albert.testsunitintregration.repository;

import lombok.extern.log4j.Log4j2;
import org.albert.testsunitintregration.model.Product;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Log4j2
public class ProductRepository {
    public List<String> findAllNames() {
        return List.of("aaa", "bbbb", "ccccc", "d", "ee");
    }

    public List<Product> findAll() {
        return List.of(
                Product.builder().name("Crucible").price(BigDecimal.valueOf(10)).build(),
                Product.builder().name("Erdtree").price(BigDecimal.valueOf(20)).build(),
                Product.builder().name("Margit").price(BigDecimal.valueOf(30.3456)).build()
        );
    }

    public void voidMethodExample() {
       log.info("void method confirmation example");
    }
}
