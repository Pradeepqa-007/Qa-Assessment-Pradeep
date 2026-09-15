
package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {

    private static final String BASE_URL =
            ConfigReader.getProperty("apiBaseUrl");

    private static final String TOKEN =
            System.getenv("ORANGEHRM_API_TOKEN");

    private static void validateToken() {
        if (TOKEN == null || TOKEN.isBlank()) {
            throw new RuntimeException(
                    "ORANGEHRM_API_TOKEN environment variable is not set."
            );
        }
    }
    public static String getEmpNumberByEmployeeId(String employeeId) {

        validateToken();

        Response response = RestAssured
                .given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .queryParam("limit", 50)
                .queryParam("offset", 0)
                .queryParam("model", "detailed")
                .when()
                .get("/api/v2/pim/employees");

        System.out.println("List Employees Status Code: "
                + response.statusCode());

        System.out.println("List Employees Response:");
        System.out.println(response.asPrettyString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Unable to retrieve employees. Status code: "
                            + response.statusCode()
            );
        }

        int employeeCount =
                response.jsonPath().getList("data").size();

        for (int i = 0; i < employeeCount; i++) {

            String apiEmployeeId =
                    response.jsonPath()
                            .getString("data[" + i + "].employeeId");

            if (employeeId.equals(apiEmployeeId)) {

                String empNumber =
                        response.jsonPath()
                                .getString("data[" + i + "].empNumber");

                System.out.println(
                        "Employee ID: " + employeeId
                                + " -> empNumber: " + empNumber
                );

                return empNumber;
            }
        }

        throw new RuntimeException(
                "Employee not found with Employee ID: " + employeeId
        );
    }
    public static Response getEmployee(String empNumber) {

        validateToken();

        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .queryParam("model", "detailed")
                .when()
                .get("/api/v2/pim/employees/" + empNumber);
    }

    public static Response deleteEmployee(String empNumber) {

        validateToken();

        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .contentType("application/json")
                .body("{\"ids\":[" + empNumber + "]}")
                .when()
                .delete("/api/v2/pim/employees");
    }
}