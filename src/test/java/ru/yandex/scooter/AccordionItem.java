package ru.yandex.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccordionItem {

    private static final By ACCORDION_ITEM_BUTTON_SELECTOR = By.xpath(".//div[@class='accordion__button']");
    private static final By ACCORDION_ITEM_PANEL_SELECTOR = By.xpath(".//div[@class='accordion__panel']");

    private final WebDriver driver;
    private final WebElement accordionItem;

    public AccordionItem(WebDriver driver, WebElement accordionItem) {
        this.driver = driver;
        this.accordionItem = accordionItem;
    }

    public void waitForDisplayPanelText() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(accordionItem.findElement(ACCORDION_ITEM_PANEL_SELECTOR)));
    }

    public void click() {
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(accordionItem)).click();
    }

    public String getHeaderText() {
        return accordionItem.findElement(ACCORDION_ITEM_BUTTON_SELECTOR).getText();
    }

    public String getDisplayedPanelText() {
        WebElement panel = accordionItem.findElement(ACCORDION_ITEM_PANEL_SELECTOR);
        return panel.isDisplayed() ? panel.getText() : "";
    }

    public WebElement getElement() {
        return this.accordionItem;
    }
}
