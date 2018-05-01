package com.example.part_4;

import com.example.annotations.Complexity;
import com.example.common.StringEventPublisher;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static com.example.annotations.Complexity.Level.EASY;
import static com.example.annotations.Complexity.Level.MEDIUM;

public class Part4Backpressure {

    @Complexity(EASY)
    public static Flux<String> dropElementsOnBackpressure(Flux<String> upstream) {
        // TODO: apply backpressure to drop elements on downstream overwhelming
        // HINT: Flux#onBackpressureDrop
        return upstream.onBackpressureDrop();

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(MEDIUM)
    public static Flux<List<Long>> backpressureByBatching(Flux<Long> upstream) {
        // TODO: decrease emission rate by buffering elements during the second
        // HINT: Flux#window(Duration) + .flatMap( .collectList ) or MORE simply Flux#buffer(Duration)
        // OPINTA v1
        return upstream.buffer(Duration.ofSeconds(1));
        // OPINTA v2
//        return upstream
//                .window(Duration.ofSeconds(1))
//                .flatMap(subFlux -> subFlux.collectList());


//        throw new RuntimeException("Not implemented");
    }
}
