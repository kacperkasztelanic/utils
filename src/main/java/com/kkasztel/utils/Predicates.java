package com.kkasztel.utils;

import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static lombok.AccessLevel.PRIVATE;

/**
 * Utility methods for composing and creating {@link Predicate}s.
 */
@NoArgsConstructor(access = PRIVATE)
public final class Predicates {

    /**
     * Returns a stateful predicate that is true only for the first element with each distinct key. Suitable for use with {@code Stream.filter()}.
     */
    public static <T> Predicate<T> distinctBy(final Function<? super T, ?> extractor) {
        Set<Object> set = ConcurrentHashMap.newKeySet();
        return t -> set.add(extractor.apply(t));
    }

    /**
     * Combines multiple predicates with logical AND.
     */
    @SafeVarargs
    public static <T> Predicate<T> and(final Predicate<T>... predicates) {
        return stream(predicates).reduce(x -> true, Predicate::and);
    }

    /**
     * Combines multiple predicates with logical OR.
     */
    @SafeVarargs
    public static <T> Predicate<T> or(final Predicate<T>... predicates) {
        return stream(predicates).reduce(x -> false, Predicate::or);
    }
}
