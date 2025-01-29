package uz.learn.it.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import uz.learn.it.helper.CustomDoubleSerializer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanPaymentHistory {
    private long id;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double amount;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double interestPayment;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double mainPayment;

    private String date;

    private long loanId;
}
