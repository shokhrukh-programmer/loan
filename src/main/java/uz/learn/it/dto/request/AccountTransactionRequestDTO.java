package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountTransactionRequestDTO {
    @Pattern(regexp = "^(top-up|withdraw)$", message = "Type should be top-up or withdraw!")
    private String type;
    @NotNull(message = "Amount should be given!!")
    private Double amountToTopUpAndWithdraw;
}
