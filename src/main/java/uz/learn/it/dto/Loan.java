package uz.learn.it.dto;

import java.util.Date;
import java.util.StringJoiner;

public class Loan {
    private int id;
    private Date createdDate;
    private int amount;
    private int term;
    private double interestRate;
    private double balance;
    private int clientId;

    public Loan(int id, Date createdDate, int amount, int term, double interestRate, double balance, int clientId) {
        this.id = id;
        this.createdDate = createdDate;
        this.amount = amount;
        this.term = term;
        this.interestRate = interestRate;
        this.balance = balance;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Loan.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("createdDate=" + createdDate)
                .add("amount=" + amount)
                .add("term=" + term)
                .add("interestRate=" + interestRate)
                .add("balance=" + balance)
                .add("clientId=" + clientId)
                .toString();
    }
}
