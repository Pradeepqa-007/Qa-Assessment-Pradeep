package tests;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.EmployeeDetailsPage;
import pages.HeaderPage;
import pages.LoginPage;
import pages.PimPage;
import utils.ConfigReader;
import utils.CsvReader;
import utils.TestDataGenerator;
import utils.RetryAnalyzer;
import io.restassured.response.Response;
import utils.ApiClient;
public class EmployeeLifecycleTest extends BaseTest {

	@Test(retryAnalyzer = RetryAnalyzer.class)
    
    public void employeeLifecycleTest() {

        // ==========================================
        // 1. LOGIN
        // ==========================================

        LoginPage loginPage =
                new LoginPage(driver);

        DashboardPage dashboardPage =
                new DashboardPage(driver);

        loginPage.login(
                ConfigReader.getProperty("username"),
                ConfigReader.getProperty("password")
        );

        Assert.assertTrue(
                dashboardPage.isDashboardDisplayed(),
                "Dashboard was not displayed after login"
        );

        // ==========================================
        // 2. ADD NEW EMPLOYEE
        // ==========================================

        String csvPath = "src/test/resources/testdata/employee.csv";

        String[] employeeData = CsvReader.readEmployeeData(csvPath);

        String firstName = employeeData[0];
        String lastName = employeeData[1];
        String jobTitle = employeeData[2];
        String employmentStatus = employeeData[3];

        String employeeId = TestDataGenerator.generateEmployeeId();

        System.out.println("Employee ID: [" + employeeId + "]");
        System.out.println("Job Title: [" + jobTitle + "]");
        System.out.println("Employment Status: [" + employmentStatus + "]");
        String imagePath =
                new File(
                        "src/test/resources/testdata/profile.png"
                ).getAbsolutePath();

        PimPage pimPage =
                new PimPage(driver);

        AddEmployeePage addEmployeePage =
                new AddEmployeePage(driver);

        pimPage.navigateToAddEmployee();

        addEmployeePage.addEmployee(
                firstName,
                lastName,
                employeeId,
                imagePath
        );

        Assert.assertTrue(
                addEmployeePage.isEmployeeCreated(),
                "Employee was not created successfully"
        );

        // ==========================================
        // 3. SEARCH AND EDIT EMPLOYEE
        // ==========================================

        pimPage.navigateToEmployeeList();

        EmployeeDetailsPage employeePage =
                new EmployeeDetailsPage(driver);

        employeePage.searchEmployeeById(employeeId);

        Assert.assertTrue(
                employeePage.isEmployeeDisplayed(employeeId),
                "Created employee was not found"
        );

        employeePage.editEmployee();

        employeePage.openJobTab();

        employeePage.selectJobTitle(employeeData[2]);
        System.out.println("Employment Status from CSV: [" + employeeData[3] + "]");

        employeePage.selectEmploymentStatus(employeeData[3]);
        

        employeePage.saveChanges();
       
        // ==========================================
        // 4. DELETE EMPLOYEE
        // ==========================================

        pimPage.navigateToEmployeeList();

        employeePage.searchEmployeeById(employeeId);

        employeePage.deleteEmployee();

        // Search again and verify deletion
        employeePage.searchEmployeeById(employeeId);

        Assert.assertTrue(
                employeePage.isEmployeeDeleted(employeeId),
                "Employee was not deleted successfully"
        );
System.out.println("praDEPPPPPP");
        // ==========================================
        // 5. LOGOUT
        // ==========================================

//Logout
HeaderPage headerPage = new HeaderPage(driver);

headerPage.logout();

Assert.assertTrue(
 headerPage.isLoginPageDisplayed(),
 "Login page was not displayed after logout"
);
    }
}

