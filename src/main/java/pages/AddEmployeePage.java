package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddEmployeePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // =========================
    // Locators
    // =========================

    // First Name
    private By firstName =
            By.name("firstName");

    // Last Name
    private By lastName =
            By.name("lastName");

    // Employee ID
    private By employeeId =
            By.xpath(
                    "//label[normalize-space()='Employee Id']" +
                    "/ancestor::div[contains(@class,'oxd-input-group')]" +
                    "//input"
            );

    // Profile Picture
    private By profilePicture =
            By.cssSelector("input[type='file']");

    // Save button
    private By saveButton =
            By.xpath("//button[@type='submit']");

    // Personal Details heading
    private By personalDetailsHeader =
            By.xpath("//h6[normalize-space()='Personal Details']");

    // =========================
    // Constructor
    // =========================

    public AddEmployeePage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(15)
        );
    }

    // =========================
    // First Name
    // =========================

    public void enterFirstName(String value) {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        firstName
                )
        );

        field.clear();
        field.sendKeys(value);
    }

    // =========================
    // Last Name
    // =========================

    public void enterLastName(String value) {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        lastName
                )
        );

        field.clear();
        field.sendKeys(value);
    }

    // =========================
    // Employee ID
    // =========================

    public void enterEmployeeId(String value) {

        // Wait until OrangeHRM loading overlay disappears
        By loader = By.cssSelector(".oxd-form-loader");

        wait.until(
            ExpectedConditions.invisibilityOfElementLocated(loader)
        );

        // Wait until Employee ID field is clickable
        WebElement field = wait.until(
            ExpectedConditions.elementToBeClickable(employeeId)
        );

        // Select the existing value and replace it
        field.click();
        field.sendKeys(
            org.openqa.selenium.Keys.CONTROL + "a"
        );
        field.sendKeys(
            org.openqa.selenium.Keys.BACK_SPACE
        );

        // Enter the new Employee ID
        field.sendKeys(value);
    }
    // =========================
    // Profile Picture
    // =========================

    public void uploadProfilePicture(String filePath) {

        WebElement uploadField = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        profilePicture
                )
        );

        uploadField.sendKeys(filePath);
    }

    // =========================
    // Save
    // =========================

    public void clickSave() {

        WebElement save = wait.until(
                ExpectedConditions.elementToBeClickable(
                        saveButton
                )
        );

        save.click();
    }

    // =========================
    // Add Employee
    // =========================

    public void addEmployee(
            String firstNameValue,
            String lastNameValue,
            String employeeIdValue,
            String imagePath) {

        enterFirstName(firstNameValue);

        enterLastName(lastNameValue);

        enterEmployeeId(employeeIdValue);

        uploadProfilePicture(imagePath);

        clickSave();
    }

    // =========================
    // Verify Employee Created
    // =========================

    public boolean isEmployeeCreated() {

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            personalDetailsHeader
                    )
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }

    // =========================
    // Get Employee ID Value
    // =========================

    public String getEmployeeIdValue() {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        employeeId
                )
        );

        return field.getAttribute("value");
    }
}