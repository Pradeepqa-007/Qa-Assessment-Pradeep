package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {

    public static String[] readEmployeeData(String filePath) {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // Skip header
            reader.readLine();

            // Read employee data
            String line = reader.readLine();

            if (line == null || line.trim().isEmpty()) {
                throw new RuntimeException("Employee data is empty");
            }

            // Remove unwanted quotes
            line = line.replace("\"", "").trim();

            String[] data = line.split(",");

            // Remove extra spaces from each value
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].trim();
            }

            return data;

        } catch (IOException e) {

            throw new RuntimeException(
                "Unable to read employee CSV file",
                e
            );
        }
    }
}