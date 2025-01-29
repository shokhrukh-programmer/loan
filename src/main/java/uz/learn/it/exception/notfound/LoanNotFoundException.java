package uz.learn.it.exception.notfound;

import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.exception.NotFoundException;

public class LoanNotFoundException extends NotFoundException {
    public LoanNotFoundException() {
        super(ExceptionMessageConstants.LOAN_NOT_FOUND_MESSAGE);
    }

    public LoanNotFoundException(String message) {
        super(message);
    }

    public LoanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoanNotFoundException(Throwable cause) {
        super(cause);
    }
}
