package com.kkasztel.utils.io;

import java.util.function.Function;
import java.util.stream.StreamSupport;

import static com.kkasztel.utils.io.Unit.Unit;

public interface IO<T> {

    T run();

    static <T> IO<T> of(final T value) {
        return () -> value;
    }

    static IO<Unit> noop() {
        return of(Unit());
    }

    static IO<Unit> sequence(final Iterable<IO<?>> ios) {
        return StreamSupport.stream(ios.spliterator(), false)
                .reduce(noop(), IO::andThen)
                .andThen(noop());
    }

    default <U> IO<U> map(final Function<T, U> f) {
        return () -> f.apply(this.run());
    }

    default <U> IO<U> flatMap(final Function<T, IO<U>> f) {
        return () -> f.apply(this.run()).run();
    }

    default <U> IO<U> andThen(final IO<U> io) {
        return flatMap(x -> io);
    }
}
