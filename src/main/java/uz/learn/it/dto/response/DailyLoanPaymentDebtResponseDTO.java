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
public class DailyLoanPaymentDebtResponseDTO {
    private long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonSerialize(using = CustomDoubleSerializer.class)
    private double dailyInterestAmount;

    private long loanId;
}
