package uz.learn.it.repository;

import org.springframework.stereotype.Component;
import uz.learn.it.dto.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class Storage {
    private final List<Client> clients = new ArrayList<>();
    private final List<Account> accounts = new ArrayList<>();
    private final List<UserCredentials> userCredentials = new ArrayList<>();
    private final List<TransactionHistory> operationHistories = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private final List<DailyLoanPaymentTable> dailyLoanPaymentTableList = new ArrayList<>();

    public List<Client> getClients() {
        return clients;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public List<UserCredentials> getUserCredentials() {
        return userCredentials;
    }

    public void addUserLoginDetails(UserCredentials userCredential) {
        userCredentials.add(userCredential);
    }

    public List<TransactionHistory> getOperationHistories() {
        return operationHistories;
    }

    public void addOperationToHistory(TransactionHistory transactionHistory) {
        operationHistories.add(transactionHistory);
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public List<DailyLoanPaymentTable> getLoanPaymentTableList() {
        return dailyLoanPaymentTableList;
    }

    public void addToPaymentTable(DailyLoanPaymentTable dailyLoanPaymentTable) {
        dailyLoanPaymentTableList.add(dailyLoanPaymentTable);
    }
}
