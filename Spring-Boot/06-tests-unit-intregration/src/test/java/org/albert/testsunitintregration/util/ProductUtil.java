package org.albert.testsunitintregration.util;

import org.albert.testsunitintregration.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductUtil {
    public static List<String> findAllNamesUtil() {
        return List.of("aaa", "bbbb", "ccccc", "d", "ee");
    }

    public static List<String> findNamesWithEvenLengthUtil() {
        return findAllNamesUtil().stream()
                .filter(name -> name.length() % 2 == 0)
                .collect(Collectors.toList());
    }

    public static List<Product> findAllUtil() {
        return List.of(
                Product.builder().name("Crucible").price(BigDecimal.valueOf(10)).build(),
                Product.builder().name("Erdtree").price(BigDecimal.valueOf(20)).build(),
                Product.builder().name("Margit").price(BigDecimal.valueOf(30.3456)).build()
        );
    }

}
