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
public class LoanResponseDTO {
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double amount;

    private int term;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double interestRate;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double balance;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double debt;

    private long clientId;
}
