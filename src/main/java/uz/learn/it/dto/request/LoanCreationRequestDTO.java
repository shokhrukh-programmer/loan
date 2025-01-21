package uz.learn.it.dto.request;

import lombok.Data;

@Data
public class LoanCreationRequestDTO {
    private long clientId;

    private double loanAmount;

    private double interestRate;

    private int loanTerm;
}
