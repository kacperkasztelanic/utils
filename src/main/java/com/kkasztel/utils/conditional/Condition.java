package com.kkasztel.utils.conditional;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

/**
 * Functional conditional matching. Builds a list of {@link Statement}s and returns the first matching action.
 */
@NoArgsConstructor(access = PRIVATE)
public final class Condition {

    /** Creates a {@link Statement} from a condition and an action. */
    public static <T> Statement<T> whether(final Supplier<Boolean> condition, final Supplier<T> action) {
        return Statement.of(condition, action);
    }

    /** Returns the action of the first statement whose condition is true. */
    @SafeVarargs
    public static <T> Optional<Supplier<T>> match(final Statement<T>... statements) {
        return Stream.of(statements)
                .filter(s -> s.getCondition().get())
                .findFirst()
                .map(Statement::getAction);
    }
}
