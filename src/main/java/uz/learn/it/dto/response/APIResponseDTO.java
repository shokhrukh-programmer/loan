package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponseDTO<T> {
    private int code;

    private String message;

    private T data;

    public APIResponseDTO() {
        this.code = 200;
        this.message = "OK";
    }
}
