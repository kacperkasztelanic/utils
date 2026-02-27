package com.kkasztel.utils.conditional;

import java.util.function.Supplier;

import lombok.Value;

/**
 * A pair of a boolean condition and a lazily-evaluated action, used with {@link Condition}.
 *
 * @param <T> the result type of the action
 */
@Value(staticConstructor = "of")
public class Statement<T> {

    Supplier<Boolean> condition;
    Supplier<T> action;
}
