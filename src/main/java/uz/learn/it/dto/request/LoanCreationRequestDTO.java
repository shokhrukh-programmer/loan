package uz.learn.it.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoanCreationRequestDTO {
    private long clientId;

    @Min(value = 0, message = "Loan amount can not be negative number!")
    private double loanAmount;

    @Min(value = 0, message = "Interest rate can not be negative number!")
    private double interestRate;

    @Min(value = 0, message = "Loan term can not be negative number!")
    private int loanTerm;
}
