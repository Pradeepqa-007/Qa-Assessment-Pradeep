package pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Employee List - Search
    private By employeeIdSearch = By.xpath(
        "//label[normalize-space()='Employee Id']" +
        "/ancestor::div[contains(@class,'oxd-input-group')]" +
        "//input"
    );
    private By jobTab = By.xpath(
    	    "//a[normalize-space()='Job']"
    	);
    private By searchButton = By.xpath(
        "//button[@type='submit']"
    );

    // Employee actions
    private By editButton = By.xpath(
        "//div[contains(@class,'oxd-table-cell-actions')]//button[1]"
    );

    private By deleteButton = By.xpath(
        "//div[contains(@class,'oxd-table-cell-actions')]//button[2]"
    );

    private By confirmDeleteButton = By.xpath(
        "//button[normalize-space()='Yes, Delete']"
    );

    // Job Title dropdown
    private By jobTitleDropdown = By.xpath(
        "//label[normalize-space()='Job Title']" +
        "/parent::div/following-sibling::div" +
        "//div[contains(@class,'oxd-select-text')]"
    );

    // Employment Status dropdown
    private By employmentStatusDropdown = By.xpath(
        "//label[normalize-space()='Employment Status']" +
        "/parent::div/following-sibling::div" +
        "//div[contains(@class,'oxd-select-text')]"
    );

    private By saveButton = By.xpath(
        "//button[@type='submit']"
    );

    // OrangeHRM loading overlay
    private By loader = By.cssSelector(
        ".oxd-form-loader"
    );


    public EmployeeDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(20)
        );
    }


    // =========================
    // SEARCH EMPLOYEE
    // =========================

    public void searchEmployeeById(String id) {

        waitForLoaderToDisappear();

        WebElement searchField = wait.until(
            ExpectedConditions.presenceOfElementLocated(employeeIdSearch)
        );

        // Scroll the Employee ID field to the center of the screen
        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
            searchField
        );

        // Small wait for the page to settle
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Click after scrolling
        wait.until(
            ExpectedConditions.elementToBeClickable(employeeIdSearch)
        ).click();

        // Clear existing value
        searchField.sendKeys(Keys.CONTROL + "a");
        searchField.sendKeys(Keys.BACK_SPACE);

        // Enter Employee ID
        searchField.sendKeys(id);

        System.out.println(
            "Searching Employee ID: [" + id + "]"
        );

        // Click Search
        WebElement search = wait.until(
            ExpectedConditions.elementToBeClickable(searchButton)
        );

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});",
            search
        );

        search.click();

        waitForLoaderToDisappear();
    }
    
    // =========================
    // EDIT EMPLOYEE
    // =========================

    public void editEmployee() {

        waitForLoaderToDisappear();

        WebElement edit = wait.until(
            ExpectedConditions.elementToBeClickable(
                editButton
            )
        );

        edit.click();

        waitForLoaderToDisappear();
    }


    // =========================
    // SELECT JOB TITLE
    // =========================
    public void openJobTab() {

        waitForLoaderToDisappear();

        WebElement jobTabElement = wait.until(
                ExpectedConditions.elementToBeClickable(jobTab)
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                jobTabElement
        );

        waitForLoaderToDisappear();

        jobTabElement.click();

        waitForLoaderToDisappear();
    }

    public void selectJobTitle(String jobTitle) {

        waitForLoaderToDisappear();

        By jobTitleDropdown = By.xpath(
            "//label[normalize-space()='Job Title']" +
            "/ancestor::div[contains(@class,'oxd-input-group')]" +
            "//div[contains(@class,'oxd-select-text')]"
        );

        WebElement dropdown = wait.until(
            ExpectedConditions.elementToBeClickable(jobTitleDropdown)
        );

        dropdown.click();

        By option = By.xpath(
            "//div[@role='option'][normalize-space(.)='" + jobTitle + "']"
        );

        wait.until(
            ExpectedConditions.elementToBeClickable(option)
        ).click();
    }
    // =========================
    // SELECT EMPLOYMENT STATUS
    // =========================
    public void selectEmploymentStatus(String employmentStatus) {

        waitForLoaderToDisappear();

        By employmentStatusDropdown = By.xpath(
            "//label[normalize-space()='Employment Status']" +
            "/ancestor::div[contains(@class,'oxd-input-group')]" +
            "//div[contains(@class,'oxd-select-text')]"
        );

        WebElement dropdown = wait.until(
            ExpectedConditions.elementToBeClickable(employmentStatusDropdown)
        );

        // Click the dropdown
        dropdown.click();

        System.out.println("Employment Status dropdown opened");

        if (employmentStatus.trim().equalsIgnoreCase("Freelance")) {

            // Use Actions instead of sendKeys() on the div
            Actions actions = new Actions(driver);

            actions
                .sendKeys(Keys.ARROW_DOWN)
                .sendKeys(Keys.ENTER)
                .perform();

        } else {

            throw new IllegalArgumentException(
                "Unsupported Employment Status: [" +
                employmentStatus + "]"
            );
        }

        System.out.println(
            "Employment Status selected: [" +
            employmentStatus + "]"
        );
    }
    // =========================
    // SAVE CHANGES
    // =========================

    public void saveChanges() {

        waitForLoaderToDisappear();

        wait.until(
            ExpectedConditions.elementToBeClickable(
                saveButton
            )
        ).click();

        waitForLoaderToDisappear();
    }

    
    // =========================
    // DELETE EMPLOYEE
    // =========================

    public void deleteEmployee() {

        waitForLoaderToDisappear();

        wait.until(
            ExpectedConditions.elementToBeClickable(
                deleteButton
            )
        ).click();

        wait.until(
            ExpectedConditions.elementToBeClickable(
                confirmDeleteButton
            )
        ).click();

        waitForLoaderToDisappear();
    }


    // =========================
    // VERIFY EMPLOYEE DISPLAYED
    // =========================

    public boolean isEmployeeDisplayed(String employeeId) {

        By employeeRow = By.xpath(
            "//div[contains(@class,'oxd-table-row')]" +
            "[.//div[contains(@class,'oxd-table-cell')]//div[normalize-space()='" 
            + employeeId + "']]"
        );

        try {

            WebElement row = wait.until(
                ExpectedConditions.visibilityOfElementLocated(employeeRow)
            );

            System.out.println(
                "Employee found with ID: [" + employeeId + "]"
            );

            return row.isDisplayed();

        } catch (Exception e) {

            System.out.println(
                "Employee NOT found with ID: [" + employeeId + "]"
            );

            return false;
        }
    }

    // =========================
    // VERIFY EMPLOYEE DELETED
    // =========================

    public boolean isEmployeeDeleted(String employeeId) {

        By employee = By.xpath(
            "//div[contains(@class,'oxd-table-cell')]" +
            "[normalize-space()='" + employeeId + "']"
        );

        try {

            return driver.findElements(employee)
                    .stream()
                    .noneMatch(
                        WebElement::isDisplayed
                    );

        } catch (Exception e) {

            return true;
        }
    }


    // =========================
    // LOADER HANDLING
    // =========================

    private void waitForLoaderToDisappear() {

        By loader = By.cssSelector(".oxd-form-loader");

        try {

            wait.until(driver ->
                    driver.findElements(loader).stream()
                            .noneMatch(element -> {
                                try {
                                    return element.isDisplayed();
                                } catch (Exception e) {
                                    return false;
                                }
                            })
            );

        } catch (Exception e) {

            System.out.println(
                    "Loader wait completed or loader was not present."
            );
        }
    
    }
    }
