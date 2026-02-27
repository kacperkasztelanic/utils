package com.kkasztel.utils;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import static java.util.stream.Collectors.toList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static com.kkasztel.utils.Optionals.ifEmpty;
import static com.kkasztel.utils.Optionals.ifPresentOrElse;
import static com.kkasztel.utils.Optionals.maybe;
import static com.kkasztel.utils.Optionals.none;
import static com.kkasztel.utils.Optionals.some;
import static com.kkasztel.utils.Optionals.stream;

class OptionalsTest {

    @Test
    void maybeReturnsPresent() {
        assertEquals(Optional.of("a"), maybe("a"));
    }

    @Test
    void maybeReturnsEmptyForNull() {
        assertEquals(Optional.empty(), maybe(null));
    }

    @Test
    void someReturnsPresent() {
        assertEquals(Optional.of("x"), some("x"));
    }

    @Test
    void noneReturnsEmpty() {
        assertEquals(Optional.empty(), none());
    }

    @Test
    void ifPresentOrElseCallsConsumerWhenPresent() {
        final AtomicReference<String> ref = new AtomicReference<>();
        final AtomicBoolean ran = new AtomicBoolean(false);
        ifPresentOrElse(Optional.of("hello"), ref::set, () -> ran.set(true));
        assertEquals("hello", ref.get());
        assertFalse(ran.get());
    }

    @Test
    void ifPresentOrElseCallsRunnableWhenEmpty() {
        final AtomicBoolean ran = new AtomicBoolean(false);
        final AtomicReference<String> ref = new AtomicReference<>();
        ifPresentOrElse(Optional.empty(), ref::set, () -> ran.set(true));
        assertTrue(ran.get());
    }

    @Test
    void ifEmptyCallsRunnableWhenEmpty() {
        final AtomicBoolean ran = new AtomicBoolean(false);
        ifEmpty(Optional.empty(), () -> ran.set(true));
        assertTrue(ran.get());
    }

    @Test
    void ifEmptyDoesNotCallRunnableWhenPresent() {
        final AtomicBoolean ran = new AtomicBoolean(false);
        ifEmpty(Optional.of("x"), () -> ran.set(true));
        assertFalse(ran.get());
    }

    @Test
    void streamReturnsSingleElementForPresent() {
        assertEquals(1, stream(Optional.of("x")).collect(toList()).size());
        assertEquals("x", stream(Optional.of("x")).collect(toList()).get(0));
    }

    @Test
    void streamReturnsEmptyForEmpty() {
        assertEquals(0, stream(Optional.empty()).collect(toList()).size());
    }
}
