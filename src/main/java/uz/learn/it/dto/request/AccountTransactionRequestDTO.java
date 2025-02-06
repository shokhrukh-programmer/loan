package uz.learn.it.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountTransactionRequestDTO {
    @NotNull(message = "Payment type can not be null")
    @Pattern(regexp = "^(TOP_UP|WITHDRAW)$", message = "Type should be TOP_UP or WITHDRAW!")
    private String type;

    @NotNull(message = "Amount can not be null!")
    @DecimalMin(value = "0.0", message = "Amount must be a non-negative number")
    private Double amountToTopUpAndWithdraw;
}
