package ru.zzemlyanaya.takibot.core.utils;

/* created by zzemlyanaya on 08/10/2022 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final ConfigReader INSTANCE = new ConfigReader();

    private final Properties properties;

    private ConfigReader() {
        this.properties = new Properties();
        this.load();
    }

    public static ConfigReader getInstance() {
        return INSTANCE;
    }

    private void load() {
        try (InputStream stream = ConfigReader.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new IllegalStateException("Failed load bot config file", e);
        }
    }

    public String get(String name) {
        return properties.getProperty(name);
    }

}