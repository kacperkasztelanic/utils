package com.kkasztel.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static lombok.AccessLevel.PRIVATE;
import lombok.NoArgsConstructor;

/**
 * Utility for creating in-memory ZIP archives from a map of filenames to byte contents.
 */
@NoArgsConstructor(access = PRIVATE)
public final class ZipUtil {

    /**
     * Creates a ZIP archive containing the given entries.
     *
     * @param map a map of entry names to their byte content
     * @return the ZIP archive as a byte array
     * @throws IOException if an I/O error occurs
     */
    public static byte[] zip(final Map<String, byte[]> map) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (final Map.Entry<String, byte[]> e : map.entrySet()) {
                final ZipEntry entry = new ZipEntry(e.getKey());
                entry.setSize(e.getValue().length);
                zos.putNextEntry(entry);
                zos.write(e.getValue());
            }
            zos.closeEntry();
        }
        return baos.toByteArray();
    }
}
