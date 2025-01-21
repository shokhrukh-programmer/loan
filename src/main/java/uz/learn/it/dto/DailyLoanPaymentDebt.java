package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class DailyLoanPaymentDebt {
    private long id;

    private String date;

    private double dailyInterestAmount;

    private long loanId;
}
