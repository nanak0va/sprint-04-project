package ru.yandex;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.common.BaseTest;
import ru.yandex.common.OrderButtonType;
import ru.yandex.scooter.HomePage;
import ru.yandex.scooter.OrderFormPage;

@RunWith(Parameterized.class)
public class PlacingOrderTest extends BaseTest {

    private static final String DONT_SHOW_ORDER_FIRST_STEP_FORM = "Не отобразилась форма первого шага оформления аренды";
    private static final String DONT_SHOW_ORDER_SECOND_STEP_FORM = "Не отобразилась форма второго шага оформления аренды";
    private static final String DONT_SHOW_ORDER_CONFIRMATION_FORM = "Не отобразилась форма подтверждения заказа";
    private static final String DONT_SHOW_ORDER_CONFIRMED_FORM = "Не отобразилась форма с информацией, что заказ подтвержден";

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;
    private final OrderButtonType buttonType;

    public PlacingOrderTest(String firstName, String lastName, String address, String metroStation, String phone, String date, String rentalPeriod, String color, String comment, OrderButtonType buttonType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
        this.buttonType = buttonType;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Иван", "Иванов", "2-й Полевой пер., 2, Москва, 107014", "Сокольники", "+79991234567", "TBD", "двое суток", "чёрный", "Ну и селекторы", OrderButtonType.BOTTOM},
                {"Петр", "Петров", "Ломоносовский просп., 27, Москва, 119192", "Университет", "+79991234568", "TBD", "сутки", "серый", "Спасибо React-у за наши бессонные ночи", OrderButtonType.TOP},
        };
    }

    @Test
    public void placeAnOrderFullPositiveCase() {

        HomePage homePage = new HomePage(driver);
        homePage.waitLoadHomePage();

        if (buttonType == OrderButtonType.BOTTOM) {
            homePage.clickOnOrderBottomButton();
        } else if (buttonType == OrderButtonType.TOP) {
            homePage.clickOnOrderTopButton();
        }

        OrderFormPage orderFormPage = new OrderFormPage(driver);
        Assert.assertTrue(DONT_SHOW_ORDER_FIRST_STEP_FORM, orderFormPage.waitForShowOrderFirstStep());

        orderFormPage.fillFormFirstStepAndNext(firstName, lastName, address, metroStation, phone);
        Assert.assertTrue(DONT_SHOW_ORDER_SECOND_STEP_FORM, orderFormPage.waitForShowOrderSecondStep());

        orderFormPage.fillFormSecondStepAndNext(date, rentalPeriod, color, comment);
        Assert.assertTrue(DONT_SHOW_ORDER_CONFIRMATION_FORM, orderFormPage.waitForShowOrderModalFormConfirm());

        orderFormPage.clickConfirmButton();
        Assert.assertTrue(DONT_SHOW_ORDER_CONFIRMED_FORM, orderFormPage.waitForOrderModalFormConfirmed());
    }
}
