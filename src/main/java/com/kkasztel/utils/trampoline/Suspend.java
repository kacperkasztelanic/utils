package com.kkasztel.utils.trampoline;

import java.util.function.Supplier;

import lombok.Value;

import static java.util.stream.Stream.iterate;

@Value(staticConstructor = "of")
class Suspend<T> implements TailCall<T> {

    Supplier<TailCall<T>> thunk;

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public TailCall<T> next() {
        return thunk.get();
    }

    @Override
    public T eval() {
        return iterate((TailCall<T>) this, TailCall::next)
                .filter(TailCall::isComplete)
                .findFirst()
                .orElseThrow(IllegalStateException::new)
                .eval();
    }
}
