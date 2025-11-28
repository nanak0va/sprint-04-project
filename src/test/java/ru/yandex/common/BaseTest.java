package ru.yandex.common;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.yandex.configs.BrowserType;
import ru.yandex.scooter.Cookies;

public class BaseTest {

    public final String uriResourceUnderTest = "https://qa-scooter.praktikum-services.ru/";

    private final BrowserType browser = BrowserType.CHROME;

    public WebDriver driver;

    @Before
    public void setUp() {
        this.driver = createWebDriver(browser);
        openResource();
        disableCookiesPanel(uriResourceUnderTest);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    //убираю плашку с предложением об использовании куки
    //она может перекрывать нижние элементы на странице, мешая тестам
    public void disableCookiesPanel(String domainUri) {
        Cookies cookies = new Cookies(driver, domainUri);
        cookies.addCookies();
        driver.navigate().refresh();
    }

    public void openResource() {
        driver.get(uriResourceUnderTest);
    }

    protected WebDriver createWebDriver(BrowserType browser) {
        switch (browser) {
            case CHROME:
                return createChromeDriver();
            case FIREFOX:
                return createFirefoxDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private ChromeDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        return new ChromeDriver(options);
    }

    private FirefoxDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        return new FirefoxDriver();
    }
}
