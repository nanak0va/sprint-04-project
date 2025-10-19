package ru.yandex.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderFormPage {

    public static final By COMMENT_INPUT = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    public static final By BLACK_CHECKBOX = By.id("black");
    public static final By GREY_CHECKBOX = By.id("grey");
    public static final By DATE_INPUT = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    public static final By DATA_INPUT_CALENDAR = By.xpath(".//div[contains(@class, 'react-datepicker__day--keyboard-selected')]");
    public static final By RENTAL_PERIOD = By.xpath(".//div[@class='Dropdown-control']");

    private static final By ORDER_FORM_SELECTOR = By.className("Order_Content__bmtHS");
    private static final By FIRST_NAME_INPUT = By.xpath(".//input[@placeholder='* Имя']");
    private static final By LAST_NAME_INPUT = By.xpath(".//input[@placeholder='* Фамилия']");
    private static final By ADDRESS_INPUT = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    private static final By METRO_STATION_INPUT = By.xpath(".//div[contains(@class, 'select-search__value')]/input[@placeholder='* Станция метро']");

    private static final By PHONE_INPUT = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    private static final By NEXT_BUTTON = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");
    private static final By CONFIRM_BUTTON = By.xpath(".//button[contains(@class, 'Button_Button__ra12g') and text()='Да']");
    private static final By ORDER_FORM_HEADER = By.className("Order_Header__BZXOb");

    private static final By ORDER_MODAL_HEADER = By.xpath(".//div[@class='Order_ModalHeader__3FDaJ']");
    private static final String ORDER_MODAL_HEADER_TEXT = "Хотите оформить заказ?";
    private static final String ORDER_MODAL_HEADER_CONFIRMED_TEXT = "Заказ оформлен";

    private static final String ORDER_FORM_HEADER_STEP_1_TEXT = "Для кого самокат";
    private static final String ORDER_FORM_HEADER_STEP_2_TEXT = "Про аренду";

    private static final String RENTAL_PERIOD_MENU = ".//div[contains(@class, 'Dropdown-option') and text()='%s']";
    private static final String METRO_STATION_SELECT = ".//div[contains(@class, 'select-search__select')]//div[@class = 'Order_Text__2broi' and text()='%s']/parent::button";

    private final WebDriver driver;
    private final WebElement orderForm;

    public OrderFormPage(WebDriver driver) {
        this.driver = driver;
        this.orderForm = driver.findElement(ORDER_FORM_SELECTOR);
    }

    public void setFirstName(String name) {
        orderForm.findElement(FIRST_NAME_INPUT).sendKeys(name);
    }

    public void setLastName(String lastName) {
        orderForm.findElement(LAST_NAME_INPUT).sendKeys(lastName);
    }

    public void setAddress(String address) {
        orderForm.findElement(ADDRESS_INPUT).sendKeys(address);
    }

    public void setPhone(String phone) {
        orderForm.findElement(PHONE_INPUT).sendKeys(phone);
    }

    public void clickNextButton() {
        orderForm.findElement(NEXT_BUTTON).click();
    }

    public void clickConfirmButton() {
        orderForm.findElement(CONFIRM_BUTTON).click();
    }

    public void selectMetroStation(String stationName) {
        WebElement metroInput = orderForm.findElement(METRO_STATION_INPUT);
        metroInput.sendKeys(stationName);

        String xpath = String.format(METRO_STATION_SELECT, stationName);
        WebElement option = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(driver.findElement(ORDER_FORM_SELECTOR).findElement(By.xpath(xpath))));
        option.click();
    }

    public void setComment(String comment) {
        orderForm.findElement(COMMENT_INPUT).sendKeys(comment);
    }

    public void selectColor(String color) {
        switch (color.toLowerCase()) {
            case "чёрный":
                orderForm.findElement(BLACK_CHECKBOX).click();
                break;
            case "серый":
                orderForm.findElement(GREY_CHECKBOX).click();
                break;
            default:
                throw new IllegalArgumentException("Неизвестный цвет: " + color);
        }
    }

    public void setToday() {
        WebElement dataInput = orderForm.findElement(DATE_INPUT);
        dataInput.click();
        chooseNow();
    }

    public void chooseNow() {
        orderForm.findElement(DATA_INPUT_CALENDAR).click();
    }

    public void selectRentalPeriod(String rentalPeriod) {
        orderForm.findElement(RENTAL_PERIOD).click();
        String xpath = String.format(RENTAL_PERIOD_MENU, rentalPeriod);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(orderForm.findElement(By.xpath(xpath))))
                .click();
    }

    public void fillFormFirstStepAndNext(String firstName, String lastName, String address, String metroStation, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhone(phone);
        selectMetroStation(metroStation);
        clickNextButton();
    }

    public void fillFormSecondStepAndNext(String date, String rentalPeriod, String color, String comment) {
        selectColor(color);
        setComment(comment);
        //TODO всегда ставим "сегодня" из календаря на форме. Константа будет устаревать, "сегодня" может отличаться. Для учебного проекта можно оставить как есть.
        setToday();
        selectRentalPeriod(rentalPeriod);
        clickNextButton();
    }

    public void waitForShowPageFirst() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElement(
                        orderForm.findElement(ORDER_FORM_HEADER),
                        ORDER_FORM_HEADER_STEP_1_TEXT
                ));
    }

    public void waitForShowPageSecond() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElement(
                        orderForm.findElement(ORDER_FORM_HEADER),
                        ORDER_FORM_HEADER_STEP_2_TEXT
                ));
    }

    public void waitForModalFormConfirm() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElement(
                        orderForm.findElement(ORDER_MODAL_HEADER),
                        ORDER_MODAL_HEADER_TEXT
                ));
    }

    public void waitForModalFormConfirmed() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.textToBePresentInElement(
                        orderForm.findElement(ORDER_MODAL_HEADER),
                        ORDER_MODAL_HEADER_CONFIRMED_TEXT
                ));
    }


}
