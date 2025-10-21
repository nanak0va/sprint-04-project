package ru.yandex;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.common.BaseTest;
import ru.yandex.scooter.HomePage;

@RunWith(Parameterized.class)
public class BlockOfQuestionsAboutImportantTest extends BaseTest {

    private static final String MESSAGE_NOT_FOUND_ACCORD_ITEM = "Отобразился ответ отличный от ожидаемого или по заголовку \"%s\" не нашли элемент, смотри сравнение";
    private static final String MESSAGE_DONT_SHOW_FOUR_PART = "Не отобразилась раздел главной страницы «Вопросы о важном»";

    private final String heading;
    private final String expectedAnswer;

    public BlockOfQuestionsAboutImportantTest(String heading, String expectedAnswer) {
        super();
        this.heading = heading;
        this.expectedAnswer = expectedAnswer;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    @Test
    public void accordionItemClickShowExpectedDescription() {

        HomePage homePage = new HomePage(driver);
        homePage.waitLoadHomePage();

        homePage.scrollIntoFourPart();

        Assert.assertTrue(MESSAGE_DONT_SHOW_FOUR_PART, homePage.waitShowFourPart());
        Assert.assertEquals(String.format(MESSAGE_NOT_FOUND_ACCORD_ITEM, heading), expectedAnswer, homePage.findAndOpenAccordionItemByHeaderText(heading));
    }

}


