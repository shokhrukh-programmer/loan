package uz.learn.it.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.StringJoiner;

public class LoanCreationRequestDTO {
    @NotNull(message = "Client id can not be null!")
    private Integer clientId;
    @NotNull(message = "Loan amount can not be null!")
    private Integer loanAmount;
    @NotNull(message = "Interest rate can not be null!")
    private Double interestRate;
    @NotNull(message = "Loan term can not be null!")
    private Integer loanTenure;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Integer loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getLoanTenure() {
        return loanTenure;
    }

    public void setLoanTenure(Integer loanTenure) {
        this.loanTenure = loanTenure;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", LoanCreationRequestDTO.class.getSimpleName() + "[", "]")
                .add("clientId=" + clientId)
                .add("loanAmount=" + loanAmount)
                .add("interestRate=" + interestRate)
                .add("loanTenure=" + loanTenure)
                .toString();
    }
}
