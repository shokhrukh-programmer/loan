package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistory {
    private int id;
    private String date;
    private String accountNumber;
    private String operation;
    private double remainingBalance;
    private int clientId;
}
