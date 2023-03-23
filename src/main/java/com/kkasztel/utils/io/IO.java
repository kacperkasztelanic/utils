package com.kkasztel.utils.io;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

import static com.kkasztel.utils.Optionals.maybe;
import static com.kkasztel.utils.Optionals.none;
import static com.kkasztel.utils.io.Unit.Unit;
import static java.util.stream.StreamSupport.stream;
import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor(access = PRIVATE)
public class IO<T> {

    private final Effect<T> effect;

    public T run() {
        return effect.run();
    }

    public Optional<T> safeRun() {
        try {
            return maybe(effect.run());
        } catch (Exception e) {
            return none();
        }
    }

    public static <T> IO<T> of(final Effect<T> effect) {
        return new IO<>(effect);
    }

    public static IO<Unit> of() {
        return of(Unit::Unit);
    }

    public static IO<Unit> sequence(final Iterable<IO<?>> ios) {
        return stream(ios.spliterator(), false)
                .reduce(of(), IO::andThen)
                .andThen(of());
    }

    public <U> IO<U> map(final Function<T, U> f) {
        return flatMap(result -> IO.of(() -> f.apply(result)));
    }

    public IO<Unit> mapUnit(final Consumer<T> f) {
        return flatMap(result -> IO.of(() -> {
            f.accept(result);
            return Unit();
        }));
    }

    public <U> IO<U> flatMap(final Function<T, IO<U>> f) {
        return IO.of(() -> f.apply(effect.run()).run());
    }

    public  <U> IO<U> andThen(final IO<U> io) {
        return flatMap(x -> io);
    }
}
