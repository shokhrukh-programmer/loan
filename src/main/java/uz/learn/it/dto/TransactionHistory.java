package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TransactionHistory {
    private long id;

    private String date;

    private String accountNumber;

    private String operation;

    private double remainingBalance;

    private long clientId;
}
