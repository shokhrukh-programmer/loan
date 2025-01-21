package uz.learn.it.exception;

import uz.learn.it.constant.Constants;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException() {
        super(Constants.ACCOUNT_NOT_FOUND_MESSAGE);
    }

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
