package uz.learn.it.repository;

import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.TransactionHistory;

import java.util.List;

public interface TransactionDAO {
    List<TransactionHistory> getTransactionHistory();

    void saveOperationToHistory(TransactionHistory transactionHistory);

    void saveDailyPayment(DailyLoanPaymentDebt dailyLoanPaymentDebt);

    List<DailyLoanPaymentDebt> getDailyDebtHistory(long loanId);
}
