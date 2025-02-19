package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCreationRequestDTO {
    private long clientId;

    @NotNull(message = "Account type can not be null!")
    @Pattern(regexp = "^(ACCOUNT|DEPOSIT)$", message = "Account type should be ACCOUNT or DEPOSIT!")
    private String accountType;
}
