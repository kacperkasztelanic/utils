package com.kkasztel.utils.tuple;

import lombok.ToString;
import lombok.Value;

import java.util.Map;

@Value(staticConstructor = "of")
@ToString(includeFieldNames = false)
public class Pair<L, R> {

    L left;
    R right;

    public static <L, R> Pair<L, R> of(final Pair<? extends L, ? extends R> pair) {
        return of(pair.getLeft(), pair.getRight());
    }

    public static <L, R> Pair<L, R> of(final Map.Entry<? extends L, ? extends R> entry) {
        return of(entry.getKey(), entry.getValue());
    }

    public L getFirst() {
        return left;
    }

    public R getSecond() {
        return right;
    }

    public L getKey() {
        return left;
    }

    public R getValue() {
        return right;
    }
}
