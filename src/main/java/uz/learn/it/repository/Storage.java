package uz.learn.it.repository;

import uz.learn.it.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Storage {
    private static long accountId = 1;
    private static long clientId = 1;
    private static long userCredentialId = 1;
    private static long loanId = 1;
    private static long historyId = 1;
    private static long paymentId = 1;
    private static long loanPaymentId = 1;
    private static final List<Client> clients = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();
    private static final List<UserCredentials> userCredentials = new ArrayList<>();
    private static final List<TransactionHistory> operationHistories = new ArrayList<>();
    private static final List<Loan> loans = new ArrayList<>();
    private static final List<DailyLoanPaymentDebt> dailyLoanPaymentDebtList = new ArrayList<>();
    private static final List<LoanPaymentHistory> loanPaymentHistoryList = new ArrayList<>();

    public static List<UserCredentials> getUserCredentials() {
        return List.copyOf(userCredentials);
    }

    public static List<Client> getClients() {
        return List.copyOf(clients);
    }

    public static List<Account> getAccounts() {
        return List.copyOf(accounts);
    }

    public static List<TransactionHistory> getOperationHistories() {
        return List.copyOf(operationHistories);
    }

    public static List<Loan> getLoans() {
        return List.copyOf(loans);
    }

    public static List<LoanPaymentHistory> getLoanPaymentHistoryList() {
        return List.copyOf(loanPaymentHistoryList);
    }

    public static boolean addClient(Client client) {
        client.setId(clientId++);
        return clients.add(client);
    }

    public static boolean addLoanPayment(LoanPaymentHistory loanPaymentHistory) {
        loanPaymentHistory.setId(loanPaymentId++);
        return loanPaymentHistoryList.add(loanPaymentHistory);
    }

    public static boolean addAccount(Account account) {
        account.setId(accountId++);
        return accounts.add(account);
    }

    public static boolean addUserLoginDetails(UserCredentials userCredential) {
        userCredential.setId(userCredentialId++);
        return userCredentials.add(userCredential);
    }

    public static boolean addOperationToHistory(TransactionHistory transactionHistory) {
        transactionHistory.setId(historyId++);
        return operationHistories.add(transactionHistory);
    }

    public static boolean addLoan(Loan loan) {
        loan.setId(loanId++);
        return loans.add(loan);
    }

    public static boolean addToPaymentTable(DailyLoanPaymentDebt dailyLoanPaymentDebt) {
        dailyLoanPaymentDebt.setId(paymentId++);
        return dailyLoanPaymentDebtList.add(dailyLoanPaymentDebt);
    }

    public static Optional<Client> findClientById(Long clientId) {
        return clients
                .stream()
                .filter(client -> client.getId() == clientId)
                .findFirst();
    }

    public static List<Account> getAccountsByClientId(Long clientId) {
        return accounts.stream()
                .filter(account -> account.getClientId() == clientId)
                .collect(Collectors.toList());
    }

    public static Optional<Account> findAccountById(Long accountId) {
        return accounts
                .stream()
                .filter(account -> account.getId() == accountId)
                .findFirst();
    }

    public static List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId) {
        return dailyLoanPaymentDebtList.stream()
                .filter(loan -> loan.getLoanId() == loanId)
                .collect(Collectors.toList());
    }

    public static Optional<Loan> findLoanById(Long loanId) {
        return loans
                .stream()
                .filter(loan -> loan.getId() == loanId)
                .findFirst();
    }

    public static Optional<Account> findAccountByAccountNumber(String accountNumber) {
        return accounts
                .stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst();
    }
}
