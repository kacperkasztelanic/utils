package com.kkasztel.utils.trampoline;

import java.util.function.Supplier;

public interface TailCall<T> {

    boolean isComplete();

    TailCall<T> next();

    T eval();

    static <T> Done<T> done(final T t) {
        return Done.of(t);
    }

    static <T> Suspend<T> suspend(final Supplier<TailCall<T>> s) {
        return Suspend.of(s);
    }
}
