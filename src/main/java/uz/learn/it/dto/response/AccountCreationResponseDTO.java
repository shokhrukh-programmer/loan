package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationResponseDTO {
    private String clientFirstName;
    private String clientLastName;
    private String accountType;
    private String accountNumber;
}
