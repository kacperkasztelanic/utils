package com.kkasztel.utils;

import java.util.Objects;
import java.util.function.BiFunction;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class IterableUtil {

    public static <T, U> U foldLeft(Iterable<T> iterable, U identity,
            BiFunction<? super U, ? super T, ? extends U> combine) {
        Objects.requireNonNull(combine, "combine is null");
        U xs = identity;
        for (T x : iterable) {
            xs = combine.apply(xs, x);
        }
        return xs;
    }
}
