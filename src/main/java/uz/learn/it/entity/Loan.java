package uz.learn.it.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.learn.it.helper.CustomDoubleSerializer;

@Data
@AllArgsConstructor
@Builder
public class Loan {
    private long id;

    private String createdDate;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double amount;

    private int term;

    private double interestRate;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double balance;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double debt;

    private long clientId;
}
