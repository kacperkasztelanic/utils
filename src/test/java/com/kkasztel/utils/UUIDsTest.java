package com.kkasztel.utils;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UUIDsTest {

    @Test
    void uuidGeneratesRandomUUID() {
        final UUID uuid = UUIDs.uuid();
        assertNotNull(uuid);
    }

    @Test
    void uuidParsesValidString() {
        final String str = "550e8400-e29b-41d4-a716-446655440000";
        final UUID uuid = UUIDs.uuid(str);
        assertEquals(str, uuid.toString());
    }

    @Test
    void uuidThrowsOnInvalidString() {
        assertThrows(IllegalArgumentException.class, () -> UUIDs.uuid("not-a-uuid"));
    }

    @Test
    void isValidReturnsTrueForValidUUID() {
        assertTrue(UUIDs.isValid("550e8400-e29b-41d4-a716-446655440000"));
        assertTrue(UUIDs.isValid(UUID.randomUUID().toString()));
    }

    @Test
    void isValidReturnsFalseForInvalidStrings() {
        assertFalse(UUIDs.isValid(null));
        assertFalse(UUIDs.isValid(""));
        assertFalse(UUIDs.isValid("not-a-uuid"));
        assertFalse(UUIDs.isValid("550e8400-e29b-41d4-a716"));
        assertFalse(UUIDs.isValid("550e8400-e29b-41d4-a716-44665544000z"));
    }

    @Test
    void isValidPredicateWorks() {
        assertTrue(UUIDs.isValid().test("550e8400-e29b-41d4-a716-446655440000"));
        assertFalse(UUIDs.isValid().test("invalid"));
        assertFalse(UUIDs.isValid().test(null));
    }
}
