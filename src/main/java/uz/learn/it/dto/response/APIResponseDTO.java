package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class APIResponseDTO<T> {
    @Builder.Default
    private int code = HttpStatus.OK.value();

    @Builder.Default
    private String message = HttpStatus.OK.getReasonPhrase();

    private T data;
}
