package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCredentialResponseDTO {
    private long id;

    private String username;

    private String password;

    private long clientId;
}