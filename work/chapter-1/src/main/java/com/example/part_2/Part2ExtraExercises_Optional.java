package com.example.part_2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.BaseStream;

import com.example.annotations.Complexity;
import com.example.annotations.Optional;
import org.reactivestreams.Publisher;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import static com.example.annotations.Complexity.Level.EASY;
import static com.example.annotations.Complexity.Level.HARD;

public class Part2ExtraExercises_Optional {

	@Optional
	@Complexity(EASY)
	public static String firstElementFromSource(Flux<String> source) {
		// TODO: block until first emitted
		// HINT: Flux#blockFirst
        return source.blockFirst();

//		throw new RuntimeException("Not implemented");
	}

	@Optional
	@Complexity(EASY)
	public static Publisher<String> mergeSeveralSourcesOrdered(Publisher<String>... sources) {
		// TODO: merge all sources in one stream with order keeping
		/*
		 * HINT: Flux#mergeSequential for eager, parallel publisher subscribing and merging
		 *       in order in which producers (publishers) have been passed as parameters
		 */
		return Flux.mergeSequential(sources);

//		throw new RuntimeException("Not implemented");
	}

	@Optional
	@Complexity(EASY)
	public static Publisher<String> concatSeveralSourcesOrdered(Publisher<String>... sources) {
		// TODO: merge all sources in one stream with order keeping but in lazy fashion
		/*
		 * HINT: Flux#cocat for lazy, sequential publisher subscribing and merging
		 *       in order in which producers (publishers) have been passed as parameters
		 *       The main difference is that we will subscribe to the next publisher only when the previous
		 *       has been completed.
		 */

		return Flux.concat(sources);

//		throw new RuntimeException("Not implemented");
	}

//    static AtomicBoolean isDisposed = new AtomicBoolean();
//
//    static Disposable disposableInstance = new Disposable() {
//        @Override
//        public void dispose() {
//            isDisposed.set(true);
//        }
//
//        @Override
//        public String toString() {
//            return "DISPOSABLE";
//        }
//    };

	@Optional
	@Complexity(HARD)
	public static Publisher<String> readFile(String filename) {
		// TODO: implement reactive file reading
		// HINT: consider Flux.using operator and Files.lines()
        // OPINTA v1
//        return Flux.using(() -> Files.lines(Paths.get(filename)), Flux::fromStream, s -> s.close());
        // OPINTA v2
        // can't see resources for method 'using'
        return Flux.using(
        		() -> Files.lines(Paths.get(filename)),
                stringStream -> Flux.fromStream(stringStream),
                s -> s.close());

//		throw new RuntimeException("Not implemented");
	}
}
