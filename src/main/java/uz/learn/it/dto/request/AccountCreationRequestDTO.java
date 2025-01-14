package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.StringJoiner;

public class AccountCreationRequestDTO {
    @NotNull(message = "Client id can not be null")
    private Integer clientId;
    @Pattern(regexp = "^(ACCOUNT|DEPOSIT)$", message = "Account type should be ACCOUNT or DEPOSIT!")
    private String accountType;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountCreationRequestDTO.class.getSimpleName() + "[", "]")
                .add("clientId=" + clientId)
                .add("accountType='" + accountType + "'")
                .toString();
    }
}
