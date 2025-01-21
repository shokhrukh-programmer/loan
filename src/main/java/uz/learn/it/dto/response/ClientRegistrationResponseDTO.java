package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientRegistrationResponseDTO {
    private String username;

    private String password;
}
