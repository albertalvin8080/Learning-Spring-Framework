package org.albert.routerfunctionclient.proxies;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.albert.routerfunctionclient.model.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;

@Log4j2
@Component
@RequiredArgsConstructor
public class ProductProxy {

    private final WebClient webClient;

    public Flux<Product> getAll() {
        final Product aDefault = Product.builder().name("Default").build();

        return webClient
                .get()
                .uri("/product")
                .exchangeToFlux(resp -> resp.bodyToFlux(Product.class))
                .doOnNext(p -> { if(p.getName() == null) throw new RuntimeException("Null Name"); })
//                .onErrorResume(e -> Flux.just(aDefault))
//                .onErrorResume(WebClientException.class, e -> Flux.just(aDefault))
//                .onErrorResume(e -> e.getMessage() != null, e -> Flux.just(aDefault));
//                .onErrorReturn(aDefault)
//                .onErrorReturn(WebClientException.class, aDefault)
//                .onErrorReturn(e -> e.getMessage() != null, aDefault);
//                .onErrorMap(e -> new RuntimeException(e));
//                .onErrorContinue((e, o) -> System.out.println(o))
//                .onErrorContinue(RuntimeException.class, (e, o) -> System.out.println(o))
                .onErrorContinue((e) -> e.getMessage() != null, (e, o) -> System.out.println(o))
        ;
    }
}
