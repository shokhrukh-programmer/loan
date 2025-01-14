package uz.learn.it.helper;

import java.security.SecureRandom;

public class PasswordGenerator {
    public static String generatePassword() {
        String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCase = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialChars = "!@#$%^&*()-_+=<>?";
        String allChars = upperCase + lowerCase + digits + specialChars;

        // Use SecureRandom for strong randomness
        SecureRandom random = new SecureRandom();

        // StringBuilder for constructing the password
        StringBuilder password = new StringBuilder(10);

        // Ensure at least one character from each group
        password.append(upperCase.charAt(random.nextInt(upperCase.length())));
        password.append(lowerCase.charAt(random.nextInt(lowerCase.length())));
        password.append(digits.charAt(random.nextInt(digits.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));

        // Fill the remaining characters from the entire pool
        for (int i = 4; i < 10; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        // Shuffle the password for randomness
        StringBuilder shuffledPassword = new StringBuilder(password.length());
        while (password.length() > 0) {
            int index = random.nextInt(password.length());
            shuffledPassword.append(password.charAt(index));
            password.deleteCharAt(index);
        }

        return shuffledPassword.toString();
    }
}
