package com.kkasztel.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

/**
 * Convenience factory methods and utilities for {@link Optional}.
 * Provides backports of some Java 9+ Optional methods for Java 8 environments.
 */
@NoArgsConstructor(access = PRIVATE)
public final class Optionals {

    /** Wraps a possibly null value in an {@link Optional}. */
    public static <T> Optional<T> maybe(final T value) {
        return Optional.ofNullable(value);
    }

    /** Wraps a non-null value in an {@link Optional}. */
    public static <T> Optional<T> some(final T value) {
        return Optional.of(value);
    }

    /** Returns an empty {@link Optional}. */
    public static <T> Optional<T> none() {
        return Optional.empty();
    }

    /** Performs the consumer if present, otherwise runs the runnable. (Java 9+ backport.) */
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

    /** Runs the runnable if the optional is empty. */
    public static <T> void ifEmpty(final Optional<T> optional, final Runnable runnable) {
        ifPresentOrElse(optional, t -> {}, runnable);
    }

    /** Converts an {@link Optional} to a {@link Stream} of zero or one element. (Java 9+ backport.) */
    public static <T> Stream<T> stream(final Optional<T> optional) {
        requireNonNull(optional, "optional is null");
        return optional.map(t -> Stream.of(t)).orElse(Stream.empty());
    }
}
