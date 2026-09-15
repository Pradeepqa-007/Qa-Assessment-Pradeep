
package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import utils.ConfigReader;

public class BaseTest {

    public WebDriver driver;
    protected WebDriverWait wait;
    public WebDriver getDriver() {
        return driver;
    }
    protected String baseUrl =
            ConfigReader.getProperty("url");
    @BeforeMethod
    public void setUp() {

        // Launch Chrome
        driver = new ChromeDriver();

        // Maximize browser
        driver.manage().window().maximize();

        // Page load timeout
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));

        // Explicit wait
        wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );

        // Open OrangeHRM
        driver.get(baseUrl);
    }

    @AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}

