package uz.learn.it.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class AccountTransactionRequestDTO {
    @NotNull(message = "Payment type can not be null")
    @Pattern(regexp = "^(TOP_UP|WITHDRAW)$", message = "Type should be TOP_UP or WITHDRAW!")
    private String type;

    @Min(value = 0, message = "Amount to do transaction can not be negative!")
    private double amountToTopUpAndWithdraw;
}
