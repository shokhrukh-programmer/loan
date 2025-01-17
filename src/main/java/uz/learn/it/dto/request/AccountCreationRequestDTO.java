package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountCreationRequestDTO {
    @NotNull(message = "Client id can not be null")
    private Integer clientId;
    @Pattern(regexp = "^(ACCOUNT|DEPOSIT)$", message = "Account type should be ACCOUNT or DEPOSIT!")
    private String accountType;
}
