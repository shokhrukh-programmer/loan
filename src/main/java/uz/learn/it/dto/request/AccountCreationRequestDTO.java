package uz.learn.it.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Pattern;

@Data
public class AccountCreationRequestDTO {
    private long clientId;

    @Pattern(regexp = "^(ACCOUNT|DEPOSIT)$", message = "Account type should be ACCOUNT or DEPOSIT!")
    private String accountType;
}
