package com.kkasztel.utils.trampoline;

import lombok.Value;

@Value(staticConstructor = "of")
public class Done<T> implements TailCall<T> {

    T t;

    @Override
    public boolean isComplete() {
        return true;
    }

    @Override
    public TailCall<T> next() {
        throw new IllegalStateException("There's no next");
    }

    @Override
    @SuppressWarnings("all")
    public T eval() {
        return t;
    }
}
