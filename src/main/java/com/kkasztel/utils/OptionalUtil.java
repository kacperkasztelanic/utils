package com.kkasztel.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("all")
@NoArgsConstructor(access = PRIVATE)
public final class OptionalUtil {

    public static <T> void ifPresentOrElse(final Optional<T> optional, final Consumer<T> consumer,
            final Runnable runnable) {
        Objects.requireNonNull(optional, "optional is null");
        Objects.requireNonNull(consumer, "consumer is null");
        Objects.requireNonNull(runnable, "runnable is null");
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
        Objects.requireNonNull(optional, "optional is null");
        return optional.map(t -> Stream.of(t)).orElse(Stream.empty());
    }
}
