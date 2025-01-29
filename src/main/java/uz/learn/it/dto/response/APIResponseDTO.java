package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.learn.it.constants.RequestCodeConstants;
import uz.learn.it.constants.SuccessfulMessageConstants;

@Data
@AllArgsConstructor
@Builder
public class APIResponseDTO<T> {
    @Builder.Default
    private int code = RequestCodeConstants.SUCCESSFUL_CODE;

    @Builder.Default
    private String message = SuccessfulMessageConstants.OK;

    private T data;
}
