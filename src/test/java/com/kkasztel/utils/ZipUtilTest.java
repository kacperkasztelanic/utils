package com.kkasztel.utils;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class ZipUtilTest {

    @Test
    void zipCreatesValidArchive() throws IOException {
        final Map<String, byte[]> entries = new LinkedHashMap<>();
        entries.put("hello.txt", "Hello World".getBytes(UTF_8));
        entries.put("data.bin", new byte[]{1, 2, 3});

        final byte[] zipBytes = ZipUtil.zip(entries);
        assertNotNull(zipBytes);

        try (final ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            final ZipEntry first = zis.getNextEntry();
            assertNotNull(first);
            assertEquals("hello.txt", first.getName());
            assertArrayEquals("Hello World".getBytes(UTF_8), readAll(zis));

            final ZipEntry second = zis.getNextEntry();
            assertNotNull(second);
            assertEquals("data.bin", second.getName());
            assertArrayEquals(new byte[]{1, 2, 3}, readAll(zis));

            assertNull(zis.getNextEntry());
        }
    }

    @Test
    void zipEmptyMapProducesValidEmptyArchive() throws IOException {
        final byte[] zipBytes = ZipUtil.zip(Collections.emptyMap());
        assertNotNull(zipBytes);
        try (final ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes))) {
            assertNull(zis.getNextEntry());
        }
    }

    private static byte[] readAll(final ZipInputStream zis) throws IOException {
        final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        final byte[] buf = new byte[1024];
        int len;
        while ((len = zis.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        return baos.toByteArray();
    }
}
