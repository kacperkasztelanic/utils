package com.kkasztel.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import static java.nio.charset.StandardCharsets.UTF_8;

public class UnicodeControl extends Control {

    @Override
    public ResourceBundle newBundle(
            final String baseName,
            final Locale locale,
            final String format,
            final ClassLoader loader,
            final boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        final String bundleName = toBundleName(baseName, locale);
        if (format.equals("java.class")) {
            return super.newBundle(baseName, locale, format, loader, reload);
        }
        if (format.equals("java.properties")) {
            if (bundleName.contains("://")) {
                return null;
            }
            final String resourceName = toResourceName(bundleName, "properties");
            final InputStream stream = reload ? reload(resourceName, loader) : loader.getResourceAsStream(resourceName);
            if (stream != null) {
                try (Reader reader = new InputStreamReader(stream, UTF_8)) {
                    return new PropertyResourceBundle(reader);
                }
            }
        }
        throw new IllegalArgumentException("Unknown format: " + format);
    }

    private InputStream reload(final String resourceName, final ClassLoader classLoader) throws IOException {
        final URL url = classLoader.getResource(resourceName);
        if (url != null) {
            final URLConnection connection = url.openConnection();
            if (connection != null) {
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        }
        return null;
    }
}
