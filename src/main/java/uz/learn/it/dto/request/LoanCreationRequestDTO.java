package uz.learn.it.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoanCreationRequestDTO {
    private long clientId;

    @NotNull(message = "Loan amount can not be null!")
    @DecimalMin(value = "0.0", message = "Amount must be a non-negative number")
    private Double loanAmount;

    @NotNull(message = "Interest rate can not be null!")
    @DecimalMin(value = "0.0", message = "Interest rate must be a non-negative number")
    private Double interestRate;

    @NotNull(message = "Loan term can not be null!")
    @DecimalMin(value = "0.0", message = "Loan term must be a non-negative number")
    private Integer loanTerm;
}
