package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanCreationRequestDTO {
    private long clientId;

    private double loanAmount;

    private double interestRate;

    private int loanTerm;
}
