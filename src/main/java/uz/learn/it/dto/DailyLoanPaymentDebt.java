package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyLoanPaymentDebt {
    private long id;

    private String date;

    private double dailyInterestAmount;

    private long loanId;
}
