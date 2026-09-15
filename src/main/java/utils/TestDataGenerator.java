package utils;

import java.util.Random;

public class TestDataGenerator {

    public static String generateEmployeeId() {

        Random random = new Random();

        int number = 10000 + random.nextInt(90000);

        return "E" + number;
    }
}