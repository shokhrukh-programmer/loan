package uz.learn.it.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.enums.AccountType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationResponseDTO {
    private String clientFirstName;

    private String clientLastName;

    private AccountType accountType;

    private String accountNumber;
}
