package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistory {
    private long id;

    private String date;

    private String accountNumber;

    private String operation;

    private double remainingBalance;

    private long clientId;
}
