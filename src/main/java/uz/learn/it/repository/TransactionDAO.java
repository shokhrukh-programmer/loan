package uz.learn.it.repository;

import uz.learn.it.entity.TransactionHistory;

import java.util.List;

public interface TransactionDAO {
    List<TransactionHistory> getTransactionHistory();

    void saveTransaction(TransactionHistory transactionHistory);
}
