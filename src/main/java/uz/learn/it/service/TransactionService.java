package uz.learn.it.service;

import uz.learn.it.dto.Account;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionHistory> getOperationHistory();
    String doTransaction(int id, AccountTransactionRequestDTO accountTransactionRequestDTO);
    Account getAccountById(int id);

}
