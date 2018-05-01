package com.example.part_8;

import com.example.annotations.Complexity;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;
import reactor.test.StepVerifier;

import static com.example.annotations.Complexity.Level.HARD;
import static com.example.annotations.Complexity.Level.MEDIUM;

public class Part8Verification {

    @Complexity(MEDIUM)
    @Test
    public void verifyThen10ElementsEmitted() {
        Flux<Integer> toVerify = Flux.fromStream(new Random().ints().boxed())
                .take(15)
                .skip(5);
        //TODO: use StepVerifier to perform testing
        // OPINTA v1
        StepVerifier
                .create(toVerify)
                .expectNextCount(10)
                .verifyComplete();

        // OPINTA v2
//        StepVerifier
//                .create(toVerify)
//                .recordWith(ArrayList::new)
//                .consumeRecordedWith(c -> Assert.assertTrue(!c.isEmpty()))
////                .expectNextMatches(integer -> Assert.assertTrue(int))
//                .verifyComplete();

        // OPINTA how to cancel
//        .thenCancel()
//                .verify()

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(HARD)
    @Test
    public void verifyEmissionWithVirtualTimeScheduler() {
        Supplier<Flux<Long>> toVerify = () -> Flux.interval(Duration.ofDays(1))
                .take(15)
                .skip(5);

        //TODO: use StepVerifier to perform testing
        // OPINTA v1
        StepVerifier
                .withVirtualTime(toVerify)
                .thenAwait(Duration.ofDays(15))
                .expectNextCount(10)
                .verifyComplete();

        // OPINTA v2
//        StepVerifier
//                .withVirtualTime(toVerify)
//                .expectSubscription()
//                .expectNoEvent(Duration.ofDays(5))
//                .thenAwait(Duration.ofDays(10))
//                .expectNextCount(10)
//                .verifyComplete();

//        throw new RuntimeException("Not implemented");
    }
}
