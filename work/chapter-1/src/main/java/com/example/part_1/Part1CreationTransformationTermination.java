package com.example.part_1;

import com.example.annotations.Complexity;
//import com.example.annotations.Optional;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import reactor.util.annotation.Nullable;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.annotations.Complexity.Level.EASY;
import static com.example.annotations.Complexity.Level.MEDIUM;

public class Part1CreationTransformationTermination {

    @Complexity(EASY)
    public static Observable<String> justABC() {
        // TODO: return "ABC" using Observable API
        // HINT: Find a static method which accept JUST one generic element
        return Observable
                .just("ABC");

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(EASY)
    public static Observable<String> fromArray(String... args) {
        // TODO: return Observable of input args
        // HINT: Find a static method which accept array of elements
        return Observable
                .from(args);

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(EASY)
    public static Observable<String> error(Throwable t) {
        // TODO: return error Observable with given Throwable

        return Observable
                .error(t);

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(EASY)
    public static Observable<Integer> convertNullableValueToObservable(@Nullable Integer nullableElement) {
        // TODO: return empty Observable if element is null otherwise return Observable from that element
        // HINT: That example requires an implementation of the similar mechanism to
        //       Optional#ofNullable
        if (nullableElement == null) {
            return Observable.empty();
        } else {
            return Observable.just(nullableElement);
        }

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(EASY)
    public static Observable<String> deferCalculation(Func0<Observable<String>> calculation) {
        // TODO: return deferred Observable
        // HINT: rx.Observable.defer()
        return Observable
                .defer(calculation);

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(EASY)
    public static Observable<Long> interval(long interval, TimeUnit timeUnit) {
        // TODO: return interval Observable
        return Observable
                .interval(interval, timeUnit);

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(EASY)
    public static Observable<String> mapToString(Observable<Long> input) {
        // TODO: map to String;
        // HINT: Use String::valueOf or Object::toString as mapping function
        return input
                .map(Object::toString);

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(EASY)
    public static Observable<String> findAllWordsWithPrefixABC(Observable<String> input) {
        // TODO: filter strings
        // HINT: use String#startsWith
        return input
                .filter(v -> v.startsWith("ABC"));

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(MEDIUM)
    public static Observable<String> fromFutureInIOScheduler(Future<String> future) {
        // TODO: return Observable from future scheduled on IO scheduler
        // HINT: rx.Observable.from(java.util.concurrent.Future<? extends T>, rx.Scheduler)
        // HINT: for IO Scheduler take a look at rx.schedulers.Schedulers.io()
        // OPINTA
        // future.get() blocks, so we use this:
        return Observable
                .from(future, Schedulers.io());

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(MEDIUM)
    public static void iterateNTimes(int times, AtomicInteger counter) {
        // TODO: refactor using Observable#range and Observable#subscribe or Observable#doOnNext
////         OPINTA v1
//        Observable
//                .range(0, times)
//                .subscribe(integer -> counter.incrementAndGet());
        // OPINTA v2
        Observable
                .range(0, times)
                .doOnNext(integer -> counter.incrementAndGet())
                .subscribe();

//        for (int i = 0; i < times; i++) {
//            counter.incrementAndGet();
//        }
    }

    @Complexity(MEDIUM)
    public static Observable<Character> flatMapWordsToCharacters(Observable<String> input) {
        // TODO: flat map strings to character
        // HINT: to split string on characters use string.split("") -> "ABC" -> [ String("A"), String("B"), String("C") ]
        // HINT: remind how to wrap array to Observable
        // HINT: consider string.charAt(0) for mapping one letter string to character
        // HINT: String("A").charAt(0) -> Char('A')
        return input
//                .flatMap(v -> v.split(""))
                .flatMap(s -> Observable.from(s.split("")))
                .map(v -> v.charAt(0));

//        throw new RuntimeException("Not implemented");
    }
    /*
     *           ^
     *          /|\
     *         / | \
     *        /  |  \
     *       /   |   \
     *           |
     *           |
     *           |
     *           |
     *
     * HINT: Imperative example
     * List<Character> list = new ArraysList<>();
     *
     * for (String word: input) {
     *    String[] letters = word.split("");
     *    for (String latter: letters) {
     *         list.add(latter.charAt(0));
     *    }
     * }
     *
     *
     *
     *
     *
     *
     */
}
