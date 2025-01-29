package uz.learn.it.dto.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.learn.it.constants.RequestCodeConstants;
import uz.learn.it.constants.SuccessfulMessageConstants;

@Data
@AllArgsConstructor
@XmlRootElement
@Builder
public class APIResponseDTO<T> {
    @XmlElement
    @Builder.Default
    private int code = RequestCodeConstants.SUCCESSFUL_CODE;

    @XmlElement
    @Builder.Default
    private String message = SuccessfulMessageConstants.OK;

    @XmlElement
    private T data;
}
