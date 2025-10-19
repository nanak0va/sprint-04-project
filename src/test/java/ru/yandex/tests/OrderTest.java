package ru.yandex.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.scooter.HomePage;
import ru.yandex.scooter.OrderFormPage;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;

    public OrderTest(String firstName, String lastName, String address, String metroStation, String phone, String date, String rentalPeriod, String color, String comment) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Иван", "Иванов", "2-й Полевой пер., 2, Москва, 107014", "Сокольники", "+79991234567", "TBD", "двое суток", "чёрный", "Ну и селекторы"},
                {"Петр", "Петров", "Ломоносовский просп., 27, Москва, 119192", "Университет", "+79991234568", "TBD", "сутки", "серый", "Спасибо React-у за наши бессонные ночи"},
        };
    }

    @Test
    public void placeAnOrderBottomButtonReceiveConfirmationForm() {

        HomePage homePage = new HomePage(driver);
        homePage.waitForLoadPage();
        homePage.clickOnOrderButtonBottom();

        placeAnOrderFullPositiveCase();
    }

    @Test
    public void placeAnOrderTopButtonReceiveConfirmationForm() {

        HomePage homePage = new HomePage(driver);
        homePage.waitForLoadPage();
        homePage.clickOnOrderButtonTop();

        placeAnOrderFullPositiveCase();
    }

    private void placeAnOrderFullPositiveCase() {
        OrderFormPage orderFormPage = new OrderFormPage(driver);

        orderFormPage.waitForShowPageFirst();

        orderFormPage.fillFormFirstStepAndNext(firstName, lastName, address, metroStation, phone);

        orderFormPage.waitForShowPageSecond();

        orderFormPage.fillFormSecondStepAndNext(date, rentalPeriod, color, comment);

        orderFormPage.waitForModalFormConfirm();

        orderFormPage.clickConfirmButton();

        orderFormPage.waitForModalFormConfirmed();
    }

}
