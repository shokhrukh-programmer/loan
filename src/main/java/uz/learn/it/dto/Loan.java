package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Loan {
    private int id;
    private String createdDate;
    private int amount;
    private int term;
    private double interestRate;
    private double balance;
    private double debt;
    private int clientId;
}
