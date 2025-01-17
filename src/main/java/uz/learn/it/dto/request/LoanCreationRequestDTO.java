package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoanCreationRequestDTO {
    @NotNull(message = "Client id can not be null!")
    private Integer clientId;
    @NotNull(message = "Loan amount can not be null!")
    private Integer loanAmount;
    @NotNull(message = "Interest rate can not be null!")
    private Double interestRate;
    @NotNull(message = "Loan term can not be null!")
    private Integer loanTerm;
}
