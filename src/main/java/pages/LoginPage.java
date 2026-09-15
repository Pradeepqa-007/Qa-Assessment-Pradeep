package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By username =
            By.name("username");

    private By password =
            By.name("password");

    private By loginButton =
            By.cssSelector("button[type='submit']");

    private By loginError =
            By.cssSelector(".oxd-alert-content-text");

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    public void enterUsername(String usernameValue) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(username)
        ).sendKeys(usernameValue);
    }

    public void enterPassword(String passwordValue) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        ).sendKeys(passwordValue);
    }

    public void clickLogin() {

        wait.until(
                ExpectedConditions.elementToBeClickable(loginButton)
        ).click();
    }

    public void login(
            String usernameValue,
            String passwordValue) {

        enterUsername(usernameValue);

        enterPassword(passwordValue);

        clickLogin();
    }

    public boolean isLoginErrorDisplayed() {

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(loginError)
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
}
