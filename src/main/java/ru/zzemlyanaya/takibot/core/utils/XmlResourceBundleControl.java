package ru.zzemlyanaya.takibot.core.utils;

/* created by zzemlyanaya on 18/10/2022 */

import org.apache.commons.text.StringEscapeUtils;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Xml resource bundle control
 * @author slabbe
 */

public class XmlResourceBundleControl extends ResourceBundle.Control {
    private static final String XML = "xml";

    /**
     * See documentation:
     * The ResourceBundle.Control.getFormats(String) method is called
     * to get resource bundle formats to produce bundle or resource names.
     */
    public List<String> getFormats(String baseName) {
        return Collections.singletonList(XML);
    }

    @Override
    public ResourceBundle newBundle(
            String baseName,
            Locale locale,
            String format,
            ClassLoader loader,
            boolean reload
    ) throws IOException {
        if ((baseName == null) || (locale == null) || (format == null) || (loader == null)) {
            throw new IllegalArgumentException("baseName, locale, format and loader cannot be null");
        }
        if (!format.equals(XML)) {
            throw new IllegalArgumentException("format must be xml");
        }

        final String bundleName = toBundleName(baseName, locale);
        final String resourceName = toResourceName(bundleName, format);
        final URL url = loader.getResource(resourceName);
        if (url == null) {
            return null;
        }

        final URLConnection urlconnection = url.openConnection();
        if (urlconnection == null) {
            return null;
        }

        if (reload) {
            urlconnection.setUseCaches(false);
        }

        try (final InputStream stream = urlconnection.getInputStream();
             final BufferedInputStream bis = new BufferedInputStream(stream)
        ) {
            return new XmlResourceBundle(bis);
        }
    }

    private static class XmlResourceBundle extends ResourceBundle {
        private final Properties props;

        public XmlResourceBundle(InputStream stream) throws IOException {
            props = new Properties();
            props.loadFromXML(stream);
        }

        @Override
        protected Object handleGetObject(@NotNull String key) {
            return StringEscapeUtils.unescapeJava(props.getProperty(key));
        }

        @NotNull
        @Override
        public Enumeration<String> getKeys() {
            Set<String> handleKeys = props.stringPropertyNames();
            return Collections.enumeration(handleKeys);
        }
    }
}
