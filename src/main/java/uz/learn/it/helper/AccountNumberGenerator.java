package uz.learn.it.helper;

import java.util.Random;

public class AccountNumberGenerator {
    public static String generateAccountNumber() {
        Random random = new Random();

        // Generate the remaining 18 digits as a string
        StringBuilder number = new StringBuilder("20208");
        for (int i = 0; i < 15; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }

    public static String generateDepositNumber() {
        Random random = new Random();

        // Generate the remaining 18 digits as a string
        StringBuilder number = new StringBuilder("22618");
        for (int i = 0; i < 15; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }
}
