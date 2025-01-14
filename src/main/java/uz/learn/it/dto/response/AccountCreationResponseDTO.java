package uz.learn.it.dto.response;

import java.util.StringJoiner;

public class AccountCreationResponseDTO {
    private String clientFirstName;
    private String clientLastName;
    private String accountType;
    private String accountNumber;

    public AccountCreationResponseDTO(String clientFirstName, String clientLastName, String accountType, String accountNumber) {
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountCreationResponseDTO.class.getSimpleName() + "[", "]")
                .add("clientFirstName='" + clientFirstName + "'")
                .add("clientLastName='" + clientLastName + "'")
                .add("accountType=" + accountType)
                .add("accountNumber='" + accountNumber + "'")
                .toString();
    }
}
