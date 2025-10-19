package ru.yandex.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private static final By HOME_PAGE_SELECTOR = By.className("Home_HomePage__ZXKIX");

    private final WebDriver driver;

    private final HomePageFourPart fourPart;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.fourPart = new HomePageFourPart(driver);
    }

    public void waitForLoadPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(driver -> (driver.findElement(HOME_PAGE_SELECTOR)));
    }

    public HomePageFourPart getHomePageFourPart() {
        return fourPart;
    }
}
