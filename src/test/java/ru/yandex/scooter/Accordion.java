package ru.yandex.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Accordion {

    private static final By ACCORDION_SELECTOR = By.xpath(".//div[@class='accordion']");
    private static final By ACCORDION_ITEMS_SELECTOR = By.xpath(".//div[@class='accordion__item']");

    private final WebDriver driver;
    private final WebElement parent;
    private WebElement accordion;
    private ArrayList<AccordionItem> accordionItems;


    public Accordion(WebDriver driver, WebElement parent) {
        this.driver = driver;
        this.parent = parent;
    }

    private WebElement getElement() {
        if (accordion == null) {
            accordion = parent.findElement(ACCORDION_SELECTOR);
        }
        return accordion;
    }

    public ArrayList<AccordionItem> getAccordionItems() {
        if (accordionItems == null) {
            accordionItems = this.getElement().findElements(ACCORDION_ITEMS_SELECTOR).stream()
                    .map(element -> new AccordionItem(driver, element))
                    .collect(ArrayList::new, List::add, List::addAll);
        }
        return accordionItems;
    }

    public AccordionItem getAccordionItemByText(String text) {
        return getAccordionItems().stream().filter(item -> item.getHeaderText().equals(text)).findFirst().orElse(null);
    }
}
