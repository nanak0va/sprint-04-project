package ru.yandex.common;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Utils {

    static final Duration DefaultWaitDuration = Duration.ofSeconds(10);

    public static WebElement waitForElement(WebDriver driver, By selector) {
        return new WebDriverWait(driver, DefaultWaitDuration).until(d -> d.findElement(selector));
    }

    public static void scrollIntoElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    public static void scrollIntoElement(WebDriver driver, By selector) {
        WebElement element = driver.findElement(selector);
        scrollIntoElement(driver, element);
    }

    public static WebElement waitForElementBeClickable(WebDriver driver, WebElement element) {
        return new WebDriverWait(driver, DefaultWaitDuration)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForElementBeClickable(WebDriver driver, By selector) {
        WebElement element = driver.findElement(selector);
        return waitForElementBeClickable(driver, element);
    }

    public static boolean waitForTextToBePresentInElement(WebDriver driver, WebElement element, String text) {
        try {
            new WebDriverWait(driver, DefaultWaitDuration)
                    .until(ExpectedConditions.textToBePresentInElement(
                            element,
                            text
                    ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean waitForTextToBePresentInElement(WebDriver driver, By selector, String text) {
        WebElement element = driver.findElement(selector);
        return waitForTextToBePresentInElement(driver, element, text);
    }

    public static void waitForVisibilityOfElement(WebDriver driver, WebElement element) {
        new WebDriverWait(driver, DefaultWaitDuration).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForVisibilityOfElement(WebDriver driver, By selector) {
        WebElement element = driver.findElement(selector);
        waitForVisibilityOfElement(driver, element);
    }
}
