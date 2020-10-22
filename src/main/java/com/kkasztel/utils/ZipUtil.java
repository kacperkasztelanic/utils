package com.kkasztel.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ZipUtil {

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
