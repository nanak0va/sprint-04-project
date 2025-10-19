package ru.yandex.scooter;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import ru.yandex.configs.ConfigReader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

public class Cookies {

    private final WebDriver driver;

    public Cookies(WebDriver driver) {
        this.driver = driver;
    }

    public String getDomain() {
        try {
            URI uri = new URI(ConfigReader.getUrl());
            return uri.getHost();
        } catch (URISyntaxException e) {
            return "";
        }
    }

    private Cookie customCookie(String cookieName, String cookieValue, String domain, String path) {
        Date expiryDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        return new Cookie.Builder(cookieName, cookieValue)
                .domain(domain)
                .path(path)
                .expiresOn(expiryDate)
                .isSecure(true)
                .build();
    }

    public void addCookies() {
        driver.manage().addCookie(customCookie("Cartoshka", "true", getDomain(), "/"));
        driver.manage().addCookie(customCookie("Cartoshka-legacy", "true", getDomain(), "/"));
    }
}
