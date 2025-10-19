package ru.yandex.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.yandex.scooter.Utils.scrollIntoElement;

public class HomePage {

    public static final By ORDER_BUTTON_TOP = By.xpath(".//button[@class='Button_Button__ra12g']");
    public static final By ORDER_BUTTON_BOTTOM = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
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

    public void clickOnOrderButtonTop() {
        scrollIntoElement(driver, ORDER_BUTTON_TOP);
        waitOrderButtonBeClickable(ORDER_BUTTON_TOP);
        driver.findElement(ORDER_BUTTON_TOP).click();
    }

    public void clickOnOrderButtonBottom() {
        scrollIntoElement(driver, ORDER_BUTTON_BOTTOM);
        waitOrderButtonBeClickable(ORDER_BUTTON_BOTTOM);
        driver.findElement(ORDER_BUTTON_BOTTOM).click();
    }

    public void waitOrderButtonBeClickable(By selector) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        driver.findElement(selector)
                ));
    }

}
