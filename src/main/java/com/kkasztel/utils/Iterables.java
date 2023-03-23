package com.kkasztel.utils;

import com.kkasztel.utils.tuple.Pair;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.kkasztel.utils.Optionals.maybe;
import static com.kkasztel.utils.Optionals.none;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class Iterables {

    public static <T, U> U foldLeft(final Iterable<T> iterable,
                                    final U identity,
                                    final BiFunction<? super U, ? super T, ? extends U> combine) {
        requireNonNull(combine, "combine is null");
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
        requireNonNull(iterable, "iterable is null");
        final Iterator<T> it = iterable.iterator();
        return it.hasNext() ? maybe(it.next()) : none();
    }

    public static <T, U> List<Pair<T, U>> zip(final Iterable<T> first, final Iterable<U> second) {
        final Iterator<T> firstIt = first.iterator();
        final Iterator<U> secondIt = second.iterator();
        final List<Pair<T, U>> res = new ArrayList<>();
        while (firstIt.hasNext() && secondIt.hasNext()) {
            res.add(Pair.of(firstIt.next(), secondIt.next()));
        }
        return res;
    }

    public static <T> List<Pair<T, Integer>> zipWithIndex(final Iterable<T> iterable) {
        final Iterator<T> it = iterable.iterator();
        final List<Pair<T, Integer>> res = new ArrayList<>();
        for (int i = 0; it.hasNext(); i++) {
            res.add(Pair.of(it.next(), i));
        }
        return res;
    }
}
