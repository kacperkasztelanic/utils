package com.kkasztel.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Provides function memoization backed by a {@link ConcurrentHashMap}.
 *
 * @param <T> the argument type
 * @param <U> the return type
 */
@NoArgsConstructor(access = PRIVATE)
public final class Memo<T, U> {

    private final Map<T, U> cache = new ConcurrentHashMap<>();

    /** Returns a memoized version of the given function. */
    public static <T, U> Function<T, U> memoize(final Function<T, U> f) {
        return new Memo<T, U>().doMemoize(f);
    }

    private Function<T, U> doMemoize(final Function<T, U> f) {
        return param -> cache.computeIfAbsent(param, f);
    }
}
