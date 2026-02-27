package com.kkasztel.utils.io;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class IOTest {

    @Test
    void runExecutesEffect() {
        final IO<String> io = IO.of(() -> "hello");
        assertEquals("hello", io.run());
    }

    @Test
    void mapTransformsResult() {
        final IO<Integer> io = IO.of(() -> "hello").map(String::length);
        assertEquals(5, io.run());
    }

    @Test
    void flatMapChainsEffects() {
        final IO<String> io = IO.of(() -> 42).flatMap(n -> IO.of(() -> "value=" + n));
        assertEquals("value=42", io.run());
    }

    @Test
    void andThenSequencesEffects() {
        final AtomicInteger counter = new AtomicInteger(0);
        final IO<String> io = IO.of(counter::incrementAndGet)
                .andThen(IO.of(() -> "done"));
        assertEquals("done", io.run());
        assertEquals(1, counter.get());
    }

    @Test
    void safeRunReturnsOptionalOnSuccess() {
        final Optional<String> result = IO.of(() -> "ok").safeRun();
        assertTrue(result.isPresent());
        assertEquals("ok", result.get());
    }

    @Test
    void safeRunReturnsEmptyOnException() {
        final Optional<String> result = IO.<String>of(() -> {
            throw new RuntimeException("fail");
        }).safeRun();
        assertFalse(result.isPresent());
    }

    @Test
    void sequenceExecutesAllEffectsInOrder() {
        final AtomicInteger counter = new AtomicInteger(0);
        final IO<Unit> io = IO.sequence(Arrays.asList(
                IO.of(counter::incrementAndGet),
                IO.of(counter::incrementAndGet),
                IO.of(counter::incrementAndGet)
        ));
        io.run();
        assertEquals(3, counter.get());
    }

    @Test
    void ofNoArgCreatesUnitIO() {
        final IO<Unit> io = IO.of();
        assertEquals(Unit.Unit(), io.run());
    }

    @Test
    void mapUnitExecutesConsumer() {
        final AtomicInteger ref = new AtomicInteger(0);
        final IO<Unit> io = IO.of(() -> 42).mapUnit(ref::set);
        io.run();
        assertEquals(42, ref.get());
    }
}
