package uz.learn.it.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoanCreationRequestDTO {
    private long clientId;

    @Min(value = 1, message = "Amount must be greater than or equal to 1!")
    private double loanAmount;

    @Min(value = 1, message = "Interest rate must be greater than or equal to 1!")
    private double interestRate;

    @Min(value = 1, message = "Loan term must be greater than or equal to 1!")
    private int loanTerm;
}
