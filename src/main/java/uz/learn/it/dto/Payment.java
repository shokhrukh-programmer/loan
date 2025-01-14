package uz.learn.it.dto;

import java.util.StringJoiner;

public class Payment {
    private int id;
    private int loanId;
    private String paymentType;
    private double paymentAmount;

    public Payment(int id, int loanId, String paymentType, double paymentAmount) {
        this.id = id;
        this.loanId = loanId;
        this.paymentType = paymentType;
        this.paymentAmount = paymentAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Payment.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("loanId=" + loanId)
                .add("paymentType='" + paymentType + "'")
                .add("paymentAmount=" + paymentAmount)
                .toString();
    }
}
