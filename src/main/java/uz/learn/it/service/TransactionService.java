package uz.learn.it.service;

import uz.learn.it.entity.Account;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionHistory> getOperationHistory();

    void makeTransaction(long id, AccountTransactionRequestDTO accountTransactionRequestDTO);

    Account getAccountByAccountId(long accountId);

}
