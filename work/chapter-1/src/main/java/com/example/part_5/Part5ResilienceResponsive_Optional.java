package com.example.part_5;

import java.util.function.Function;

import com.example.annotations.Complexity;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import static com.example.annotations.Complexity.Level.HARD;

public class Part5ResilienceResponsive_Optional {

	@Complexity(HARD)
	public static Publisher<Integer> provideSupportOfContinuation(Flux<Integer> values) {
		// TODO: Enable continuation strategy
		// Provide additional fix in test to add Hooks#onErrorDrop to enable global errors hooks

        Hooks.onErrorDropped(throwable -> System.out.println(throwable));

        return values.errorStrategyContinue();
//        return values;
	}

	@Complexity(HARD)
	public static Publisher<Integer> provideSupportOfContinuationWithoutErrorStrategy(Flux<Integer> values, Function<Integer, Integer> mapping) {
		// TODO: handle errors using flatting

//        Mono.fromCallable(() -> 10 / 0)
//                .onErrorResume(throwable -> Mono.empty());

        return values.flatMap(integer -> Mono
                .fromCallable(() -> mapping.apply(integer))
                .onErrorResume(throwable -> Mono.empty()));

//		return values.map(mapping);
	}
}
