package com.kkasztel.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Memo<T, U> {

    private final Map<T, U> cache = new ConcurrentHashMap<>();

    public static <T, U> Function<T, U> memoize(final Function<T, U> f) {
        return new Memo<T, U>().doMemoize(f);
    }

    private Function<T, U> doMemoize(final Function<T, U> f) {
        return param -> cache.computeIfAbsent(param, f);
    }
}
