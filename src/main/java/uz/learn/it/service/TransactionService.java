package uz.learn.it.service;

import jakarta.servlet.http.HttpServletRequest;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.TransactionHistoryResponseDTO;
import uz.learn.it.entity.Account;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    List<TransactionHistoryResponseDTO> getOperationHistory(int page, int size, LocalDate fromDate, LocalDate toDate);

    void makeTransaction(long id, AccountTransactionRequestDTO accountTransactionRequestDTO);

    Account getAccountByAccountId(long accountId);

    List<TransactionHistoryResponseDTO> getOperationHistoryByClientId(HttpServletRequest request, int page, int size, LocalDate from, LocalDate to);
}
