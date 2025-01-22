package uz.learn.it.dto.response;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@XmlRootElement
public class APIResponseDTO<T> {
    @XmlElement
    private int code;

    @XmlElement
    private String message;

    @XmlElement
    private T data;

    public APIResponseDTO() {
        this.code = 200;
        this.message = "OK";
    }
}
