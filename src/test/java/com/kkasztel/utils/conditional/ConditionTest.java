package com.kkasztel.utils.conditional;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.function.Supplier;

import static com.kkasztel.utils.conditional.Condition.match;
import static com.kkasztel.utils.conditional.Condition.whether;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConditionTest {

    @Test
    void matchReturnsFirstTrueCondition() {
        final Optional<Supplier<String>> result = match(
                whether(() -> false, () -> "first"),
                whether(() -> true, () -> "second"),
                whether(() -> true, () -> "third")
        );
        assertTrue(result.isPresent());
        assertEquals("second", result.get().get());
    }

    @Test
    void matchReturnsEmptyWhenNoConditionMatches() {
        final Optional<Supplier<String>> result = match(
                whether(() -> false, () -> "a"),
                whether(() -> false, () -> "b")
        );
        assertFalse(result.isPresent());
    }

    @Test
    void matchWorksWithSingleStatement() {
        final Optional<Supplier<Integer>> result = match(
                whether(() -> true, () -> 42)
        );
        assertTrue(result.isPresent());
        assertEquals(42, result.get().get());
    }

    @Test
    void whetherCreatesStatement() {
        final Statement<String> statement = whether(() -> true, () -> "value");
        assertTrue(statement.getCondition().get());
        assertEquals("value", statement.getAction().get());
    }
}
