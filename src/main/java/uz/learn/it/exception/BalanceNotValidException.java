package uz.learn.it.exception;

public class BalanceNotValidException extends RuntimeException {
    public BalanceNotValidException(String message) {
        super(message);
    }
}
