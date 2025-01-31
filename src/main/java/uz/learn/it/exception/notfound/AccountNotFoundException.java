package uz.learn.it.exception.notfound;

import uz.learn.it.constant.Constants;
import uz.learn.it.exception.NotFoundException;

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
