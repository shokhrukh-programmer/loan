package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payment {
    private int id;
    private int loanId;
    private String paymentType;
    private double paymentAmount;
}
