package com.kkasztel.utils;

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class IterableUtil {

    public static <T, U> U foldLeft(final Iterable<T> iterable, final U identity,
            final BiFunction<? super U, ? super T, ? extends U> combine) {
        Objects.requireNonNull(combine, "combine is null");
        U xs = identity;
        for (T x : iterable) {
            xs = combine.apply(xs, x);
        }
        return xs;
    }

    public static <T> T head(final Iterable<T> iterable) {
        return maybeHead(iterable).orElseThrow(() -> new IndexOutOfBoundsException("Called head on an empty iterable"));
    }

    public static <T> Optional<T> maybeHead(final Iterable<T> iterable) {
        Objects.requireNonNull(iterable, "iterable is null");
        final Iterator<T> it = iterable.iterator();
        return it.hasNext() ? Optional.ofNullable(it.next()) : Optional.empty();
    }
}
