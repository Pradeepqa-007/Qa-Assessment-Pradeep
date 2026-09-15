package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import utils.ExtentReportManager;
import utils.ExtentTestManager;
import utils.ScreenshotUtil;

public class TestListener implements ITestListener {

    private ExtentReports extent =
            ExtentReportManager.getExtentReports();

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod()
                .getMethodName();

        ExtentTest test =
                extent.createTest(testName);

        ExtentTestManager.setTest(test);

        test.info("Test execution started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        ExtentTest test =
                ExtentTestManager.getTest();

        test.pass("Test passed successfully");

        System.out.println(
                "TEST PASSED: "
                + result.getName()
        );

        ExtentTestManager.removeTest();
    }

    @Override
    public void onTestFailure(ITestResult result) {

        ExtentTest test =
                ExtentTestManager.getTest();

        test.fail(
                "Test failed: "
                + result.getThrowable()
        );

        Object currentClass =
                result.getInstance();

        if (currentClass instanceof BaseTest) {

            BaseTest baseTest =
                    (BaseTest) currentClass;

            String screenshotPath =
                    ScreenshotUtil.captureScreenshot(
                            baseTest.getDriver(),
                            result.getName()
                    );

            test.addScreenCaptureFromPath(
                    screenshotPath
            );
        }

        ExtentTestManager.removeTest();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentTest test =
                ExtentTestManager.getTest();

        test.skip(
                "Test skipped: "
                + result.getThrowable()
        );

        ExtentTestManager.removeTest();
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        System.out.println(
                "Extent Report generated successfully."
        );
    }
}