package uz.learn.it.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import uz.learn.it.helper.CustomDoubleSerializer;

@Data
@AllArgsConstructor
@Builder
public class TransactionHistory {
    private long id;

    private String date;

    private String accountNumber;

    private String operation;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double remainingBalance;

    private long clientId;
}
