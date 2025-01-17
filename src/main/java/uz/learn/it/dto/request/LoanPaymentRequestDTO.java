package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoanPaymentRequestDTO {
    @NotNull(message = "Loan id can not be null!")
    private Integer loanId;
    @NotNull(message = "Payment type can not be null")
    @NotBlank(message = "Payment type can not be blank")
    @NotEmpty(message = "Payment type can not be empty")
    @Pattern(regexp = "^INTEREST|MAIN$", message = "Payment type should be INTEREST or MAIN")
    private String paymentType;
    @NotNull(message = "Amount can not be null!")
    private Double paymentAmount;
    @NotNull(message = "Account number can not be null")
    @NotBlank(message = "Account number can not be blank")
    @NotEmpty(message = "Account number can not be empty")
    private String accountNumber;
}
