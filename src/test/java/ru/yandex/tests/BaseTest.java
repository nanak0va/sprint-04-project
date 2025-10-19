package ru.yandex.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import ru.yandex.configs.ConfigReader;

public class BaseTest {

    public final String uriResourceUnderTest;

    private final BrowserType browser;

    public WebDriver driver;

    public BaseTest() {
        this.browser = ConfigReader.getBrowser();
        this.uriResourceUnderTest = ConfigReader.getUrl();
    }

    @BeforeClass
    public static void setUpClass() {
        ConfigReader.loadProperties();
    }

    @Before
    public void setUp() {
        driver = createWebDriver(browser);
        openResource();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
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

        FirefoxOptions options = new FirefoxOptions();
        return new FirefoxDriver(options);
    }

}
