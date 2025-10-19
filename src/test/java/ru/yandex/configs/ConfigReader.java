package ru.yandex.configs;

import ru.yandex.tests.BrowserType;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    public static void loadProperties() {
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Файл config.properties не найден!");
            }
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при чтении config.properties", e);
        }
    }

    public static BrowserType getBrowser() {
        String browserStr = PROPERTIES.getProperty("browser");
        try {
            return BrowserType.valueOf(browserStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверный тип браузера в конфиге: " + browserStr);
        }
    }

    public static String getUrl() {
        return PROPERTIES.getProperty("url");
    }
}