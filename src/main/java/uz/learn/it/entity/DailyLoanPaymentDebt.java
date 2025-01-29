package uz.learn.it.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.learn.it.helper.CustomDoubleSerializer;

@Data
@AllArgsConstructor
@Builder
public class DailyLoanPaymentDebt {
    private long id;

    private String date;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double dailyInterestAmount;

    private long loanId;
}
