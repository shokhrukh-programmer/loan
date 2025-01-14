package uz.learn.it.dto;

import java.util.Date;

public class DailyLoanPaymentTable {
    private int id;
    private Date date;
    private double dailyInterestAmount;
    private int loanId;

    public DailyLoanPaymentTable(int id, Date date, double dailyInterestAmount, int loanId) {
        this.id = id;
        this.date = date;
        this.dailyInterestAmount = dailyInterestAmount;
        this.loanId = loanId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDailyInterestAmount() {
        return dailyInterestAmount;
    }

    public void setDailyInterestAmount(double dailyInterestAmount) {
        this.dailyInterestAmount = dailyInterestAmount;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }
}
