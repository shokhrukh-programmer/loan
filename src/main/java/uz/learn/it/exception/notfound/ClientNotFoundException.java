package uz.learn.it.exception;

import uz.learn.it.constant.Constants;

public class ClientNotFoundException extends NotFoundException {
    public ClientNotFoundException() {
        super(Constants.CLIENT_NOT_FOUND_MESSAGE);
    }

    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientNotFoundException(Throwable cause) {
        super(cause);
    }
}
