package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.StringJoiner;

public class AccountTransactionRequestDTO {
    @Pattern(regexp = "^(top-up|withdraw)$", message = "Type should be top-up or withdraw!")
    private String type;
    @NotNull(message = "Amount should be given!!")
    private Double amountToTopUpAndWithdraw;

    public AccountTransactionRequestDTO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmountToTopUpAndWithdraw() {
        return amountToTopUpAndWithdraw;
    }

    public void setAmountToTopUpAndWithdraw(Double amountToTopUpAndWithdraw) {
        this.amountToTopUpAndWithdraw = amountToTopUpAndWithdraw;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountTransactionRequestDTO.class.getSimpleName() + "[", "]")
                .add("type=" + type)
                .add("amountToTopUp=" + amountToTopUpAndWithdraw)
                .toString();
    }
}
