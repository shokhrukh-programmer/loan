package uz.learn.it.repository;

import lombok.Getter;
import org.springframework.stereotype.Component;
import uz.learn.it.dto.*;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class Storage {
    private final List<Client> clients = new ArrayList<>();
    private final List<Account> accounts = new ArrayList<>();
    private final List<UserCredentials> userCredentials = new ArrayList<>();
    private final List<TransactionHistory> operationHistories = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();
    private final List<Payment> payments = new ArrayList<>();
    private final List<DailyLoanPaymentDebt> dailyLoanPaymentDebtList = new ArrayList<>();

    public void addClient(Client client) {
        clients.add(client);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void addUserLoginDetails(UserCredentials userCredential) {
        userCredentials.add(userCredential);
    }

    public void addOperationToHistory(TransactionHistory transactionHistory) {
        operationHistories.add(transactionHistory);
    }

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public void addToPaymentTable(DailyLoanPaymentDebt dailyLoanPaymentDebt) {
        dailyLoanPaymentDebtList.add(dailyLoanPaymentDebt);
    }
}
