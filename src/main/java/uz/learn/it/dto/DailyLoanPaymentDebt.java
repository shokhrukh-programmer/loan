package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyLoanPaymentDebt {
    private int id;
    private String date;
    private double dailyInterestAmount;
    private int loanId;
}
