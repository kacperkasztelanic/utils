package com.kkasztel.utils;

import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.kkasztel.utils.Predicates.and;
import static lombok.AccessLevel.PRIVATE;

/**
 * Utility methods for creating and validating {@link UUID}s.
 */
@NoArgsConstructor(access = PRIVATE)
public final class UUIDs {

    private static final Pattern UUID_PATTERN = Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    private static final Predicate<String> UUID_VALID = and(Objects::nonNull, i -> UUID_PATTERN.matcher(i).matches());

    /**
     * Generates a random UUID.
     */
    public static UUID uuid() {
        return UUID.randomUUID();
    }

    /**
     * Parses a UUID from its string representation.
     */
    public static UUID uuid(final String uuid) {
        return UUID.fromString(uuid);
    }

    /**
     * Returns a predicate that tests whether a string is a valid UUID.
     */
    public static Predicate<String> isValid() {
        return UUID_VALID;
    }

    /**
     * Tests whether the given string is a valid UUID.
     */
    public static boolean isValid(final String uuid) {
        return isValid().test(uuid);
    }
}
