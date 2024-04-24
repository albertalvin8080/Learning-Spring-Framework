package org.albert.publishersubscribersubscription.controller;

import org.albert.publishersubscribersubscription.publisher.DemoPublisher;
import org.albert.publishersubscribersubscription.subscriber.DemoSubscriber;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
public class DemoController {

    @GetMapping("/d1")
    public void demo1() {
        final Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6);

        flux
//                .doOnNext(System.out::println)
//                .doOnNext(o -> { if(o == 4) throw new RuntimeException(""); })
                .subscribe(new DemoSubscriber());
    }

    @GetMapping("/d2")
    public void demo2()
    {
        final DemoPublisher publisher = new DemoPublisher(List.of(1, 2, 3, 4, 5, 6, 7));
        publisher.subscribe(new DemoSubscriber());
    }

    @GetMapping("/d3")
    public void sinkDemo()
    {
        final Flux<Integer> flux =
                Flux.create(sink -> {
                    for (int i = 0; i < 10; ++i) {
                        sink.next(i);
                    }
                    sink.complete();
                });

        flux
                .log()
//                .subscribe(o -> System.out.println(o)) // doesnt use backpressure
                .subscribe(new DemoSubscriber())
        ;
    }

    @GetMapping(path = "/d4", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> fluxMethods() {
        final Flux<String> f1 = Flux.just("AA", "AA", "B", "CCC", "DDDDDD", "AA");
        final Flux<String> f2 = Flux.just("Qual", "Frieren", "awt");

        return f1
//                .log()
//                .filter(s -> s.length() % 2 == 0);
//                .log();
//                .flatMap(s -> Flux.just(s.split("")));
//                .reduce("", (a, b) -> a + " " + b);
//                .collect(Collectors.toList());
//                .distinct();
//                .distinctUntilChanged();
//                .concatWith(f2);
//                .doOnNext(System.out::println)
//                .thenMany(f2);
                .zipWith(f2, (x, y) -> x + "=" + y)
                .log();

    }
}
