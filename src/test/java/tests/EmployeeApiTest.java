package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.ConfigReader;

public class EmployeeApiTest {

    private static final String BASE_URL =
            ConfigReader.getProperty("apiBaseUrl");

    private static final String TOKEN =
            System.getenv("ORANGEHRM_API_TOKEN");

    @Test
    public void employeeApiLifecycleTest() {

        // =====================================================
        // TEST DATA
        // =====================================================

        String firstName = "Pradeep";
        String lastName = "Automation";
        String employeeId = "E" +
                System.currentTimeMillis() % 100000;

        System.out.println(
                "Employee ID: " + employeeId
        );

        // =====================================================
        // 1. CREATE EMPLOYEE
        // =====================================================

        String requestBody =
                "{"
                + "\"firstName\":\"" + firstName + "\","
                + "\"lastName\":\"" + lastName + "\","
                + "\"employeeId\":\"" + employeeId + "\""
                + "}";

        Response createResponse = RestAssured
                .given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + TOKEN
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v2/pim/employees");

        System.out.println(
                "Create Employee Status: "
                        + createResponse.statusCode()
        );

        System.out.println(
                createResponse.asPrettyString()
        );

        // Validate status
        Assert.assertEquals(
                createResponse.statusCode(),
                200,
                "Create Employee API should return 200"
        );

        // =====================================================
        // 2. CAPTURE EMP NUMBER
        // =====================================================

        String empNumber =
                createResponse.jsonPath()
                        .getString("data.empNumber");

        Assert.assertNotNull(
                empNumber,
                "empNumber should not be null"
        );

        Assert.assertFalse(
                empNumber.isBlank(),
                "empNumber should not be blank"
        );

        System.out.println(
                "Created Employee empNumber: "
                        + empNumber
        );

        // =====================================================
        // 3. VALIDATE CREATE RESPONSE
        // =====================================================

        String createdFirstName =
                createResponse.jsonPath()
                        .getString("data.firstName");

        String createdLastName =
                createResponse.jsonPath()
                        .getString("data.lastName");

        String createdEmployeeId =
                createResponse.jsonPath()
                        .getString("data.employeeId");

        Assert.assertEquals(
                createdFirstName,
                firstName,
                "First Name mismatch"
        );

        Assert.assertEquals(
                createdLastName,
                lastName,
                "Last Name mismatch"
        );

        Assert.assertEquals(
                createdEmployeeId,
                employeeId,
                "Employee ID mismatch"
        );

        // =====================================================
        // 4. GET EMPLOYEE
        // =====================================================

        Response getResponse = RestAssured
                .given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + TOKEN
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .queryParam("model", "detailed")
                .when()
                .get(
                        "/api/v2/pim/employees/"
                                + empNumber
                );

        System.out.println(
                "Get Employee Status: "
                        + getResponse.statusCode()
        );

        System.out.println(
                getResponse.asPrettyString()
        );

        // Validate status
        Assert.assertEquals(
                getResponse.statusCode(),
                200,
                "Get Employee API should return 200"
        );

        // =====================================================
        // 5. VALIDATE GET EMPLOYEE RESPONSE
        // =====================================================

        String apiFirstName =
                getResponse.jsonPath()
                        .getString("data.firstName");

        String apiLastName =
                getResponse.jsonPath()
                        .getString("data.lastName");

        String apiEmployeeId =
                getResponse.jsonPath()
                        .getString("data.employeeId");

        String apiEmpNumber =
                getResponse.jsonPath()
                        .getString("data.empNumber");

        Assert.assertEquals(
                apiFirstName,
                firstName,
                "API First Name mismatch"
        );

        Assert.assertEquals(
                apiLastName,
                lastName,
                "API Last Name mismatch"
        );

        Assert.assertEquals(
                apiEmployeeId,
                employeeId,
                "API Employee ID mismatch"
        );

        Assert.assertEquals(
                apiEmpNumber,
                empNumber,
                "API empNumber mismatch"
        );

        // =====================================================
        // 6. DELETE EMPLOYEE
        // =====================================================

        String deleteBody =
                "{\"ids\":[" + empNumber + "]}";

        Response deleteResponse = RestAssured
                .given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + TOKEN
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .contentType("application/json")
                .body(deleteBody)
                .when()
                .delete("/api/v2/pim/employees");

        System.out.println(
                "Delete Employee Status: "
                        + deleteResponse.statusCode()
        );

        System.out.println(
                deleteResponse.asPrettyString()
        );

        // Validate delete status
        Assert.assertEquals(
                deleteResponse.statusCode(),
                200,
                "Delete Employee API should return 200"
        );

        // Validate deleted ID
        String deletedId =
                deleteResponse.jsonPath()
                        .getString("data[0]");

        Assert.assertEquals(
                deletedId,
                empNumber,
                "Deleted employee number mismatch"
        );

        // =====================================================
        // 7. GET EMPLOYEE AFTER DELETE
        // =====================================================

        Response deletedEmployeeResponse = RestAssured
                .given()
                .baseUri(BASE_URL)
                .header(
                        "Authorization",
                        "Bearer " + TOKEN
                )
                .header(
                        "Accept",
                        "application/json"
                )
                .when()
                .get(
                        "/api/v2/pim/employees/"
                                + empNumber
                );

        System.out.println(
                "Get Deleted Employee Status: "
                        + deletedEmployeeResponse.statusCode()
        );

        System.out.println(
                deletedEmployeeResponse.asPrettyString()
        );

        // =====================================================
        // 8. VALIDATE 422 AFTER DELETE
        // =====================================================

        Assert.assertEquals(
                deletedEmployeeResponse.statusCode(),
                422,
                "Deleted employee should return 422 after deletion"
        );
        String errorMessage =
                deletedEmployeeResponse.jsonPath()
                        .getString("error.message");

        Assert.assertEquals(
                errorMessage,
                "Invalid Parameter",
                "Unexpected error message after employee deletion"
        );
    }
}