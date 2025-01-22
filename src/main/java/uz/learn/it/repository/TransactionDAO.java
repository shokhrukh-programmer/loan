package uz.learn.it.repository;

import uz.learn.it.dto.TransactionHistory;

public interface TransactionDAO {
    void saveOperationToHistory(TransactionHistory transactionHistory);
}
