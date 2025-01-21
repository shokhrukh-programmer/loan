package uz.learn.it.repository;

import uz.learn.it.dto.*;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Client> clients = new ArrayList<>();
    public static final List<Account> accounts = new ArrayList<>();
    public static final List<UserCredentials> userCredentials = new ArrayList<>();
    public static final List<TransactionHistory> operationHistories = new ArrayList<>();
    public static final List<Loan> loans = new ArrayList<>();
    public static final List<Payment> payments = new ArrayList<>();
    public static final List<DailyLoanPaymentDebt> dailyLoanPaymentDebtList = new ArrayList<>();

    public static void addClient(Client client) {
        clients.add(client);
    }

    public static void addAccount(Account account) {
        accounts.add(account);
    }

    public static void addUserLoginDetails(UserCredentials userCredential) {
        userCredentials.add(userCredential);
    }

    public static void addOperationToHistory(TransactionHistory transactionHistory) {
        operationHistories.add(transactionHistory);
    }

    public static void addLoan(Loan loan) {
        loans.add(loan);
    }

    public static void addPayment(Payment payment) {
        payments.add(payment);
    }

    public static void addToPaymentTable(DailyLoanPaymentDebt dailyLoanPaymentDebt) {
        dailyLoanPaymentDebtList.add(dailyLoanPaymentDebt);
    }
}
