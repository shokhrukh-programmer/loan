package uz.learn.it.constants;

import org.springframework.http.HttpStatus;

public class RequestCodeConstants {
    public static final int BAD_REQUEST_CODE = HttpStatus.BAD_REQUEST.value();

    public static final int NOT_FOUND_CODE = HttpStatus.NOT_FOUND.value();

    public static final int SUCCESSFUL_CODE = HttpStatus.OK.value();
}