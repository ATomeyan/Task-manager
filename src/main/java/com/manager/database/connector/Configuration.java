package com.manager.database.connector;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author Artur Tomeyan
 * @date 02.11.2023
 */
public final class Configuration {

    private static Configuration configuration = null;
    private final Properties properties;

    private Configuration() {
        properties = new Properties();

        try (InputStream inputStream = Files.newInputStream(Paths.get("src/main/resources/database.properties"))){

            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }

        return configuration;
    }

    public String getValue(String key) {
        return this.properties.getProperty(key);
    }
}