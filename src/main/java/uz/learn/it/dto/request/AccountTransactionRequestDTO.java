package uz.learn.it.dto.request;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AccountTransactionRequestDTO {
    @Pattern(regexp = "^(TOP_UP|WITHDRAW)$", message = "Type should be TOP_UP or WITHDRAW!")
    private String type;

    private double amountToTopUpAndWithdraw;
}
