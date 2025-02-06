package uz.learn.it.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoanPaymentRequestDTO {
    @NotNull(message = "Payment type can not be null")
    @NotBlank(message = "Payment type can not be blank")
    @NotEmpty(message = "Payment type can not be empty")
    @Pattern(regexp = "^INTEREST|MAIN$", message = "Payment type should be INTEREST or MAIN")
    private String paymentType;

    @Min(value = 0, message = "Payment amount can not be negative!")
    private double paymentAmount;

    @NotNull(message = "Account number can not be null")
    @NotBlank(message = "Account number can not be blank")
    @NotEmpty(message = "Account number can not be empty")
    private String accountNumber;
}
