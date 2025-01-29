package uz.learn.it.helper;

import uz.learn.it.constants.AccountConstants;

import java.util.Random;

public class AccountNumberGenerator {
    private static final Random random = new Random();

    public static String generateAccountNumber() {
        // Generate the remaining 18 digits as a string
        StringBuilder number = new StringBuilder(AccountConstants.ACCOUNT_NUMBER_PREFIX);

        return getGeneratedNumber(number);
    }

    public static String generateDepositNumber() {
        // Generate the remaining 18 digits as a string
        StringBuilder number = new StringBuilder(AccountConstants.DEPOSIT_NUMBER_PREFIX);

        return getGeneratedNumber(number);
    }

    private static String getGeneratedNumber(StringBuilder number) {
        for (int i = 0; i < 15; i++) {
            number.append(random.nextInt(10));
        }

        return number.toString();
    }
}
