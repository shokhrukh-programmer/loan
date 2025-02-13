package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ClientRegistrationResponseDTO {
    private String username;

    private String password;
}
