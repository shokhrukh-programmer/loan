package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Loan {
    private long id;

    private String createdDate;

    private double amount;

    private int term;

    private double interestRate;

    private double balance;

    private double debt;

    private long clientId;
}
