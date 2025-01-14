package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
