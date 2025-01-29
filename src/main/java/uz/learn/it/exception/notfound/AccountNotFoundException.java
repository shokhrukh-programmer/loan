package uz.learn.it.exception.notfound;

import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.exception.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException() {
        super(ExceptionMessageConstants.ACCOUNT_NOT_FOUND_MESSAGE);
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
