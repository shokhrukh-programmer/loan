package uz.learn.it.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountTransactionRequestDTO {
    @Pattern(regexp = "^(TOP_UP|WITHDRAW)$", message = "Type should be TOP_UP or WITHDRAW!")
    private String type;

    private double amountToTopUpAndWithdraw;
}
