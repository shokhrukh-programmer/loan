package uz.learn.it.dto;

import java.util.Date;
import java.util.StringJoiner;

public class TransactionHistory {
    private int id;
    private Date date;
    private String accountNumber;
    private String operation;
    private double remainingBalance;
    private int clientId;

    public TransactionHistory(int id, Date date, String accountNumber, String operation, double remainingBalance, int clientId) {
        this.id = id;
        this.date = date;
        this.accountNumber = accountNumber;
        this.operation = operation;
        this.remainingBalance = remainingBalance;
        this.clientId = clientId;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TransactionHistory.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("date=" + date)
                .add("accountNumber='" + accountNumber + "'")
                .add("income='" + operation + "'")
                .add("remainingBalance='" + remainingBalance + "'")
                .add("clientId='" + clientId)
                .toString();
    }
}
