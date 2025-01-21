package uz.learn.it.service;

import uz.learn.it.dto.Account;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionHistory> getOperationHistory();

    void makeTransaction(long id, AccountTransactionRequestDTO accountTransactionRequestDTO);

    Account getAccountById(long accountId);

}
