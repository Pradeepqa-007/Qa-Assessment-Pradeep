package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getExtentReports() {

        if (extent == null) {

            String reportPath =
                    "test-output/ExtentReport.html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            sparkReporter.config().setDocumentTitle(
                    "OrangeHRM Automation Test Report"
            );

            sparkReporter.config().setReportName(
                    "Employee Lifecycle Test Report"
            );

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            extent.setSystemInfo(
                    "Application",
                    "OrangeHRM"
            );

            extent.setSystemInfo(
                    "Automation",
                    "Selenium WebDriver"
            );

            extent.setSystemInfo(
                    "Language",
                    "Java"
            );

            extent.setSystemInfo(
                    "Framework",
                    "TestNG"
            );

            extent.setSystemInfo(
                    "Tester",
                    "Pradeep Kumar M"
            );
        }

        return extent;
    }
}