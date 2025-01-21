package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
    private long id;

    private long loanId;

    private String paymentType;

    private double paymentAmount;
}
