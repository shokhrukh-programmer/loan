package uz.learn.it.repository;

import uz.learn.it.entity.TransactionHistory;

import java.time.LocalDate;
import java.util.List;

public interface TransactionDAO {
    List<TransactionHistory> getTransactionHistory(int page, int size, LocalDate fromDate, LocalDate toDate);

    void saveTransaction(TransactionHistory transactionHistory);
}
