package com.example.part_1;

import com.example.annotations.Complexity;
import com.example.annotations.Optional;
import com.example.common.StringEventPublisher;
import java.util.function.Consumer;
import rx.Observable;
import rx.Subscriber;

import static com.example.annotations.Complexity.Level.EASY;
import static com.example.annotations.Complexity.Level.HARD;

public class Part1ExtraExercises_Optional {

    @Optional
    @Complexity(EASY)
    public static Observable<String> flattenObservablesOrdered(Observable<Observable<String>> input) {
        // TODO: flatten map ordered strings to character
        // HINT: rx.Observable#concatMap
        return input
                .concatMap(stringObservable -> stringObservable);

//        throw new RuntimeException("Not implemented");
    }

    /**
     * Write a program that transform the numbers from 1 to 100 to String representation.
     * But:
     * * For multiples of three map to “Fizz” instead of the number.
     * * For the multiples of five map to “Buzz”.
     * * For numbers which are multiples of both three and five map to “FizzBuzz”.
     * * For the case when non of above statements are true return string representation of a number
     *
     * @param input Input of numbers from 1 to 100
     * @see IndexedWord
     * @return Observable with mapped numbers
     */
    @Optional
    @Complexity(HARD)
    public static Observable<String> fizzBuzz(Observable<Integer> input) {
//        return input
//                .map(String::valueOf);
        return input
                .map(i -> new IndexedWord(i, ""))
                .map(iw -> iw.getIndex() %3 == 0 ? new IndexedWord(iw.getIndex(), "Fizz") : iw)
                .map(iw -> iw.getIndex() %5 == 0 ? new IndexedWord(iw.getIndex(), iw.getWord() + "Bus") : iw)
                .map(iw -> iw.getWord().isEmpty() ? String.valueOf(iw.getIndex()) : iw.getWord());

//        throw new RuntimeException("Not implemented");
    }

    @Complexity(HARD)
    public static Observable<String> adaptToObservable(StringEventPublisher eventPublisher) {
        // TODO: when subscriber of the returned Observable<String> has subscribed,
        //       they should receive data emitted from the StringEventPublisher

        // NOTE: StringEventPublisher is a simple data source to which we may subscribe in the plain java in the next way:
        //
        //       eventPublisher.registerEventListener(new Consumer<String>() {
        //           @Override
        //           public void accept(String s) {
        //               System.out.println(s);
        //           }
        //       });

        // NOTE: When you use Observable.unsafeCreate the parameter is also function which looks like next:
        //
        //        Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
        //            @Override
        //            public void call(Subscriber<? super String> subscriber) {
        //
        //            }
        //        });

        // NOTE: As we learned earlier, Subscriber has method onNext which should be called every time
        //       eventPublisher.registerEventListener(new Consumer<String>()... emits new value

        // TODO: adapt to Observable; consider Observable#unsafeCreate
        // HINT: combine eventPublisher.registerEventListener( with OnSubscribe::onNext )


        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext();
//                subscriber.onError();
//                subscriber.onCompleted();
                eventPublisher.registerEventListener(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        subscriber.onNext(s);
                    }
                });
            }
        });

//        throw new RuntimeException("Not implemented");
    }
}
