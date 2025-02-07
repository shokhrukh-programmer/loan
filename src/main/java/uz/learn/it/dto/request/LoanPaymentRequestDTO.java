package uz.learn.it.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoanPaymentRequestDTO {
    @NotNull(message = "Payment type can not be null")
    @NotBlank(message = "Payment type can not be blank")
    @Pattern(regexp = "^INTEREST|MAIN$", message = "Payment type should be INTEREST or MAIN")
    private String paymentType;

    @Min(value = 1, message = "Amount must be greater than or equal 1")
    private double paymentAmount;

    @NotNull(message = "Account number can not be null")
    @NotBlank(message = "Account number can not be blank")
    @Pattern(regexp = "^\\d{20}$", message = "Account number is not valid!")
    private String accountNumber;
}
