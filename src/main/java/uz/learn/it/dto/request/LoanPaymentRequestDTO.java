package uz.learn.it.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoanPaymentRequestDTO {
    @NotNull(message = "Payment type can not be null")
    @NotBlank(message = "Payment type can not be blank")
    @Pattern(regexp = "^INTEREST|MAIN$", message = "Payment type should be INTEREST or MAIN")
    private String paymentType;

    @NotNull(message = "Payment amount can not be null!")
    @DecimalMin(value = "0.0", message = "Amount must be a non-negative number")
    private Double paymentAmount;

    @NotNull(message = "Account number can not be null")
    @NotBlank(message = "Account number can not be blank")
    @Pattern(regexp = "^\\d{20}$", message = "Account number is not valid!")
    private String accountNumber;
}
