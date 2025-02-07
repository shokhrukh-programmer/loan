package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import uz.learn.it.constants.SuccessfulMessageConstants;

@Data
@AllArgsConstructor
@Builder
public class APIResponseDTO<T> {
    @Builder.Default
    private int code = HttpStatus.OK.value();

    @Builder.Default
    private String message = SuccessfulMessageConstants.OK;

    private T data;
}
