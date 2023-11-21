package com.kkasztel.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("all")
@NoArgsConstructor(access = PRIVATE)
public final class Optionals {

    public static <T> Optional<T> maybe(final T value) {
        return Optional.ofNullable(value);
    }

    public static <T> Optional<T> some(final T value) {
        return Optional.of(value);
    }

    public static <T> Optional<T> none() {
        return Optional.empty();
    }

    public static <T> void ifPresentOrElse(final Optional<T> optional,
                                           final Consumer<T> consumer,
                                           final Runnable runnable) {
        requireNonNull(optional, "optional is null");
        requireNonNull(consumer, "consumer is null");
        requireNonNull(runnable, "runnable is null");
        if (optional.isPresent()) {
            consumer.accept(optional.get());
        }
        else {
            runnable.run();
        }
    }

    public static <T> void ifEmpty(final Optional<T> optional, final Runnable runnable) {
        ifPresentOrElse(optional, t -> {}, runnable);
    }

    public static <T> Stream<T> stream(final Optional<T> optional) {
        requireNonNull(optional, "optional is null");
        return optional.map(t -> Stream.of(t)).orElse(Stream.empty());
    }
}
