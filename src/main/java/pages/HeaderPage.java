
package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HeaderPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By userDropdown =
            By.cssSelector(".oxd-userdropdown-tab");

    private By logoutLink =
            By.xpath("//a[normalize-space()='Logout']");

    public HeaderPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    public void logout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        userDropdown
                )
        ).click();

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        logoutLink
                )
        ).click();
    }

    public boolean isLoginPageDisplayed() {

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.name("username")
                    )
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
}


