package com.kkasztel.utils;

import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Arrays.stream;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Predicates {

    public static <T> Predicate<T> distinctBy(final Function<? super T, ?> extractor) {
        Set<Object> set = ConcurrentHashMap.newKeySet();
        return t -> set.add(extractor.apply(t));
    }

    @SafeVarargs
    public static <T> Predicate<T> and(final Predicate<T>... predicates) {
        return stream(predicates).reduce(x -> true, Predicate::and);
    }

    @SafeVarargs
    public static <T> Predicate<T> or(final Predicate<T>... predicates) {
        return stream(predicates).reduce(x -> false, Predicate::or);
    }
}
