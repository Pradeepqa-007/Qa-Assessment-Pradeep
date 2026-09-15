# OrangeHRM Employee Lifecycle - QA Automation Assessment

## 1. Setup Instructions

### Prerequisites

The following tools are required:

- Java JDK 17 or above
- Maven
- Google Chrome
- Git
- Apache JMeter for performance testing

Verify Java:

```bash
java -version

Verify Maven:

mvn -version
Application Under Test

OrangeHRM Demo:

https://opensource-demo.orangehrmlive.com/

Clone the Repository
git clone <YOUR_GITHUB_REPOSITORY_URL>

Navigate to the project:

cd Qa-Assessment-Pradeep
Configuration

Application configuration is maintained in:

src/test/resources/config.properties

Example:

url=https://opensource-demo.orangehrmlive.com/
username=Admin
password=admin123
browser=chrome

Test data is maintained in:

src/test/resources/testdata/employee.csv

The Employee ID is generated dynamically during test execution.

API credentials/tokens, if required, should be provided through secure configuration or environment variables and should not be committed to GitHub.

2. Framework Structure
Qa-Assessment-Pradeep
│
├── README.md
├── pom.xml
├── testng.xml
│
├── src
│   │
│   ├── main
│   │   └── java
│   │       └── pages
│   │           ├── LoginPage.java
│   │           ├── DashboardPage.java
│   │           ├── PimPage.java
│   │           ├── AddEmployeePage.java
│   │           ├── EmployeeDetailsPage.java
│   │           └── HeaderPage.java
│   │
│   └── test
│       │
│       ├── java
│       │   ├── base
│       │   │   └── BaseTest.java
│       │   │
│       │   ├── listeners
│       │   │   └── TestListener.java
│       │   │
│       │   ├── tests
│       │   │   └── EmployeeLifecycleTest.java
│       │   │
│       │   └── utils
│       │       ├── ConfigReader.java
│       │       ├── CsvReader.java
│       │       ├── RetryAnalyzer.java
│       │       ├── ScreenshotUtil.java
│       │       ├── ExtentReportManager.java
│       │       ├── ExtentTestManager.java
│       │       └── TestDataGenerator.java
│       │
│       └── resources
│           ├── config.properties
│           └── testdata
│               ├── employee.csv
│               └── profile.png
│
└── performance
    └── orangehrm-performance.jmx

The framework uses the Page Object Model (POM) to separate page locators and actions from test logic.

The test uses TestNG for execution and assertions.

Reusable utilities are used for:

Configuration management
CSV data reading
Dynamic Employee ID generation
Retry handling
Screenshot capture
Extent reporting
3. How to Run the Test
Using Eclipse

Right-click:

testng.xml

Select:

Run As → TestNG Suite
Using Maven

Open a terminal in the project root directory and execute:

mvn clean test

The test validates the OrangeHRM Employee Lifecycle workflow:

Login
  ↓
Dashboard Validation
  ↓
Add Employee
  ↓
Employee Creation Validation
  ↓
Search Employee
  ↓
Update Job Title
  ↓
Update Employment Status
  ↓
UI Validation
  ↓
API Validation
  ↓
UI/API Data Comparison
  ↓
Delete Employee
  ↓
API Deletion Validation
  ↓
Logout
Test Report

After execution, the Extent HTML report is available at:

test-output/ExtentReport.html

Failure screenshots are stored at:

test-output/screenshots/

## Test Execution Recording

The test execution screen recording is available in the project.

`recordings/Pradeep_Kumar_M_Test_Execution.mp4`
Performance Test

The JMeter performance test plan is available at:

performance/orangehrm-performance.jmx

Open the .jmx file using Apache JMeter and execute the configured performance test.

4. Dependencies Used

The project uses Maven for dependency management.

Selenium WebDriver

Used for web UI automation.

<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.35.0</version>
</dependency>
TestNG

Used for test execution, assertions, listeners, and retry handling.

<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.11.0</version>
    <scope>test</scope>
</dependency>
REST Assured

Used for API testing and API response validation.

<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.5.6</version>
    <scope>test</scope>
</dependency>
Extent Reports

Used to generate the HTML test execution report.

<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.2</version>
</dependency>
Apache Commons IO

Used for screenshot file handling.

<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
    <version>2.20.0</version>
</dependency>
Apache JMeter

Used separately for performance testing of the OrangeHRM application/API.
## 5. Performance Testing

Apache JMeter 5.6.3 was used to perform API performance testing on the OrangeHRM application.

### API Methods Tested

The following API methods were tested using JMeter:

- **POST – Create Employee**
- **GET – Get Employee**
- **DELETE – Delete Employee**

### POST – Create Employee

**Endpoint:**

`POST /web/index.php/api/v2/pim/employees`

The POST request is used to create a temporary employee for performance testing. The generated `empNumber` is captured from the response using a JSON Extractor and passed to the subsequent GET and DELETE requests.

**Validation:**

- Response Code: `200 OK`
- Employee creation response is validated.
- `empNumber` is extracted dynamically from the response.

### GET – Get Employee

**Endpoint:**

`GET /web/index.php/api/v2/pim/employees/${empNumber}`

The GET request uses the `empNumber` generated by the POST request to retrieve the same employee.

**Validation:**

- Response Code: `200 OK`
- Employee details are returned successfully.
- Dynamic `empNumber` is used for the request.

### DELETE – Delete Employee

**Endpoint:**

`DELETE /web/index.php/api/v2/pim/employees`

The DELETE request uses the same `empNumber` captured from the POST response to remove the temporary employee.

**Request Body:**

```json
{
  "ids": [${empNumber}]
}

This version is **clean, concise, and directly aligned with the four README it