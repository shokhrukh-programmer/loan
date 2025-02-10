package uz.learn.it.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.learn.it.helper.CustomDoubleSerializer;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionHistoryResponseDTO {
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String accountNumber;

    private String operation;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double remainingBalance;

    private long clientId;
}
