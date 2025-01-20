package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponseDTO<T> {
    private static final String OK_MESSAGE = "OK";
    private int code = 200;
    private String message = OK_MESSAGE;
    private T data;
}
