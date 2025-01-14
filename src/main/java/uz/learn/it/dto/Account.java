package uz.learn.it.dto;

import java.util.StringJoiner;

public class Account {
    private int id;
    private String accountType;
    private String accountNumber;
    private double balance;
    private int clientId;

    public Account() {
    }

    public Account(int id, String accountType, String accountNumber, double balance, int clientId) {
        this.id = id;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("accountType='" + accountType + "'")
                .add("accountNumber='" + accountNumber + "'")
                .add("balance=" + balance)
                .add("clientId=" + clientId)
                .toString();
    }
}
