package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PimPage {

    private WebDriver driver;
    private WebDriverWait wait;

    private By pimMenu =
            By.xpath("//span[text()='PIM']");

    private By addEmployeeMenu =
            By.xpath("//a[text()='Add Employee']");

    private By employeeListMenu =
            By.xpath("//a[text()='Employee List']");

    public PimPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    public void clickPIM() {

        wait.until(
                ExpectedConditions.elementToBeClickable(pimMenu)
        ).click();
    }

    public void clickAddEmployee() {

        wait.until(
                ExpectedConditions.elementToBeClickable(addEmployeeMenu)
        ).click();
    }

    public void clickEmployeeList() {

        wait.until(
                ExpectedConditions.elementToBeClickable(employeeListMenu)
        ).click();
    }

    public void navigateToAddEmployee() {

        clickPIM();

        clickAddEmployee();
    }

    public void navigateToEmployeeList() {

        clickPIM();

        clickEmployeeList();
    }
}

