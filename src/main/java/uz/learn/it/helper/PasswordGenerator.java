package uz.learn.it.helper;

import uz.learn.it.constant.Constants;

import java.security.SecureRandom;

public class PasswordGenerator {
    public static String generatePassword() {
        String allChars =
                Constants.UPPER_CASE +
                Constants.LOWER_CASE +
                Constants.DIGITS +
                Constants.SPECIAL_CHARS;

        // Use SecureRandom for strong randomness
        SecureRandom random = new SecureRandom();

        // StringBuilder for constructing the password
        StringBuilder password = new StringBuilder(10);

        // Ensure at least one character from each group
        password.append(Constants.UPPER_CASE.charAt(random.nextInt(Constants.UPPER_CASE.length())));
        password.append(Constants.LOWER_CASE.charAt(random.nextInt(Constants.LOWER_CASE.length())));
        password.append(Constants.DIGITS.charAt(random.nextInt(Constants.DIGITS.length())));
        password.append(Constants.SPECIAL_CHARS.charAt(random.nextInt(Constants.SPECIAL_CHARS.length())));

        // Fill the remaining characters from the entire pool
        for (int i = 4; i < 10; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the password for randomness
        StringBuilder shuffledPassword = new StringBuilder(password.length());
        while (!password.isEmpty()) {
            int index = random.nextInt(password.length());
            shuffledPassword.append(password.charAt(index));
            password.deleteCharAt(index);
        }

        return shuffledPassword.toString();
    }
}
