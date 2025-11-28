package ru.yandex.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.common.Utils.*;

public class HomePage {

    private static final String ERROR_ACCORDION_ITEM_NOT_FOUND = "[ERROR] Не смогли найти элемент выпадающего списка. %nЗаголовки на странице:%n%s ";
    private static final String FOUR_PART_HEADER_TEXT = "Вопросы о важном";

    private static final By HOME_PAGE_SELECTOR = By.className("Home_HomePage__ZXKIX");

    private static final By FOUR_PART_SELECTOR = By.xpath(".//div[@class='Home_FourPart__1uthg']");
    private static final By FOUR_PART_HEADER = By.xpath(".//div[@class='Home_SubHeader__zwi_E']");

    private static final By ACCORDION_SELECTOR = By.xpath(".//div[@class='accordion']");
    private static final By ACCORDION_ITEMS_SELECTOR = By.xpath(".//div[@class='accordion__item']");
    private static final By ACCORDION_ITEM_BUTTON_SELECTOR = By.xpath(".//div[@class='accordion__button']");
    private static final By ACCORDION_ITEM_PANEL_SELECTOR = By.xpath(".//div[@class='accordion__panel']");

    private static final By ORDER_BUTTON_TOP = By.xpath(".//button[@class='Button_Button__ra12g']");
    private static final By ORDER_BUTTON_BOTTOM = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    private final WebDriver driver;

    //Имена элементов аналогичны компанентам в React
    private WebElement homePage;
    private WebElement fourPart;
    private WebElement accordion;
    private ArrayList<WebElement> accordionItems;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void waitLoadHomePage() {
        getHomePage();
    }

    private WebElement getHomePage() {
        if (homePage == null) {
            this.homePage = waitForElement(driver, HOME_PAGE_SELECTOR);
        }
        return homePage;
    }

    private WebElement getHomePageFourPart() {
        if (fourPart == null) {
            return getHomePage().findElement(FOUR_PART_SELECTOR);
        }
        return fourPart;
    }

    public void scrollIntoFourPart() {
        scrollIntoElement(driver, getHomePageFourPart());
    }

    public boolean waitShowFourPart() {
        return waitForTextToBePresentInElement(driver, getHomePageFourPart().findElement(FOUR_PART_HEADER), FOUR_PART_HEADER_TEXT);
    }

    public void clickOnOrderTopButton() {
        scrollIntoElement(driver, ORDER_BUTTON_TOP);
        waitForElementBeClickable(driver, ORDER_BUTTON_TOP).click();
    }

    public void clickOnOrderBottomButton() {
        scrollIntoElement(driver, ORDER_BUTTON_BOTTOM);
        waitForElementBeClickable(driver, ORDER_BUTTON_BOTTOM).click();
    }

    private WebElement getAccordion() {
        if (accordion == null) {
            return getHomePageFourPart().findElement(ACCORDION_SELECTOR);
        }
        return accordion;
    }

    private ArrayList<WebElement> getAccordionItems() {
        if (accordionItems == null) {
            accordionItems = getAccordion().findElements(ACCORDION_ITEMS_SELECTOR).stream()
                    .collect(ArrayList::new, List::add, List::addAll);
        }
        return accordionItems;
    }

    public String findAndOpenAccordionItemByHeaderText(String headerText) {
        WebElement accordionItem = getAccordionItemByHeaderText(headerText);
        if (accordionItem == null) {
            return String.format(ERROR_ACCORDION_ITEM_NOT_FOUND, getAllAccordionHeadersAsString());
        }
        scrollIntoAccordionItem(accordionItem);
        waitForDisplayAccordionItemPanelText(accordionItem);
        clickAccordionItem(accordionItem);
        return getDisplayedPanelText(accordionItem);
    }

    private void scrollIntoAccordionItem(WebElement accordionItem) {
        waitForElementBeClickable(driver, accordionItem).click();
    }

    private void clickAccordionItem(WebElement accordionItem) {
        waitForElementBeClickable(driver, accordionItem).click();
    }

    private WebElement getAccordionItemByHeaderText(String text) {
        try {
            return getAccordionItems().stream().filter(item -> getAccordionItemHeaderText(item).equals(text)).findFirst().orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    private String getAllAccordionHeadersAsString() {
        return getAccordionItems().stream()
                .map(this::getAccordionItemHeaderText)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private void waitForDisplayAccordionItemPanelText(WebElement accordionItem) {
        waitForVisibilityOfElement(driver, accordionItem.findElement(ACCORDION_ITEM_PANEL_SELECTOR));
    }

    private String getAccordionItemHeaderText(WebElement accordionItem) {
        return accordionItem.findElement(ACCORDION_ITEM_BUTTON_SELECTOR).getText();
    }

    private String getDisplayedPanelText(WebElement accordionItem) {
        WebElement panel = accordionItem.findElement(ACCORDION_ITEM_PANEL_SELECTOR);
        return panel.isDisplayed() ? panel.getText() : "";
    }


}
