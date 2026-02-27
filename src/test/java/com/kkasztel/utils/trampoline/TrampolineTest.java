package com.kkasztel.utils.trampoline;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrampolineTest {

    @Test
    void doneIsComplete() {
        final Done<Integer> done = Done.of(42);
        assertTrue(done.isComplete());
        assertEquals(42, done.eval());
    }

    @Test
    void doneNextThrows() {
        final Done<Integer> done = Done.of(10);
        assertThrows(IllegalStateException.class, done::next);
    }

    @Test
    void suspendIsNotComplete() {
        final Suspend<Integer> suspend = Suspend.of(() -> Done.of(1));
        assertFalse(suspend.isComplete());
    }

    @Test
    void suspendEvalUnwindsToResult() {
        final Suspend<Integer> suspend = Suspend.of(() -> Done.of(99));
        assertEquals(99, suspend.eval());
    }

    @Test
    void trampolineHandlesDeepRecursion() {
        // Compute factorial(10) = 3628800 using trampoline to avoid stack overflow
        final int result = factorial(10, 1).eval();
        assertEquals(3628800, result);
    }

    @Test
    void trampolineHandlesVeryDeepRecursion() {
        // Sum from 1 to 100000 using trampoline
        final long result = sum(100000, 0L).eval();
        assertEquals(5000050000L, result);
    }

    @Test
    void factoryMethodsWork() {
        assertEquals(42, TailCall.done(42).eval());
        assertEquals(42, TailCall.suspend(() -> TailCall.done(42)).eval());
    }

    private static TailCall<Integer> factorial(final int n, final int acc) {
        if (n <= 1) {
            return TailCall.done(acc);
        }
        return TailCall.suspend(() -> factorial(n - 1, n * acc));
    }

    private static TailCall<Long> sum(final long n, final long acc) {
        if (n == 0) {
            return TailCall.done(acc);
        }
        return TailCall.suspend(() -> sum(n - 1, acc + n));
    }
}
