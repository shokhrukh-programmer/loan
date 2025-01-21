package uz.learn.it.service;

import uz.learn.it.dto.Account;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.AccountCreationResponseDTO;

import java.util.List;

public interface AccountService {
    AccountCreationResponseDTO createAccount(AccountCreationRequestDTO accountCreationRequestDTO);
    List<Account> getAccountsByClientId(Long id);

    List<Account> getAccounts();

}
