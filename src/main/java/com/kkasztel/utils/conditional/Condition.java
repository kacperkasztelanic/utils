package com.kkasztel.utils.conditional;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Condition {

    public static <T> Statement<T> whether(final Supplier<Boolean> condition, final Supplier<T> action) {
        return Statement.of(condition, action);
    }

    @SafeVarargs
    public static <T> Optional<Supplier<T>> match(final Statement<T>... statements) {
        return Stream.of(statements)
                .filter(s -> s.getCondition().get())
                .findFirst()
                .map(Statement::getAction);
    }
}
