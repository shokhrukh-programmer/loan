package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCredentialResponseDTO {
    private long id;

    private String username;

    private String password;

    private long clientId;
}