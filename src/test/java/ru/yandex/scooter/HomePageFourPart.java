package ru.yandex.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static ru.yandex.scooter.Utils.scrollIntoElement;

public class HomePageFourPart {

    private static final String FOUR_PART_HEADER_TEXT = "Вопросы о важном";

    private static final By FOUR_PART_SELECTOR = By.xpath(".//div[@class='Home_FourPart__1uthg']");
    private static final By FOUR_PART_HEADER_SELECTOR = By.xpath(".//div[@class='Home_SubHeader__zwi_E']");

    private final WebDriver driver;
    private final WebElement fourPart;

    public HomePageFourPart(WebDriver driver) {
        this.driver = driver;
        this.fourPart = getElement();
    }

    public WebElement getElement() {
        if (this.fourPart == null) {
            return driver.findElement(FOUR_PART_SELECTOR);
        }
        return this.fourPart;
    }

    public void scrollInto() {
        scrollIntoElement(driver, FOUR_PART_SELECTOR);
    }

    public void waitForShowPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElement(
                        fourPart.findElement(FOUR_PART_HEADER_SELECTOR),
                        FOUR_PART_HEADER_TEXT
                ));
    }


}
