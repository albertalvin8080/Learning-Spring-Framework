package org.albert.routerfunctionclient.services;

import lombok.RequiredArgsConstructor;
import org.albert.routerfunctionclient.model.Product;
import org.albert.routerfunctionclient.proxies.ProductProxy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductProxy productProxy;

    public Flux<Product> getAll() {
        return productProxy.getAll();
    }
}
