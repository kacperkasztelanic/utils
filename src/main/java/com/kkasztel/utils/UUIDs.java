package com.kkasztel.utils;

import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static com.kkasztel.utils.Predicates.and;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class UUIDs {

    private static final String UUID_V4_REGEX = "^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}$";
    private static final Predicate<String> UUID_VALID = and(Objects::nonNull, i -> Pattern.compile(UUID_V4_REGEX).matcher(i).matches());

    public static UUID uuid() {
        return UUID.randomUUID();
    }

    public static UUID uuid(final String uuid) {
        return UUID.fromString(uuid);
    }

    public static Predicate<String> isValid() {
        return UUID_VALID;
    }

    public static boolean isValid(final String uuid) {
        return isValid().test(uuid);
    }
}
