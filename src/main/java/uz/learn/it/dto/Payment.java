package uz.learn.it.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Payment {
    private long id;

    private long loanId;

    private PaymentType paymentType;

    private double paymentAmount;
}
