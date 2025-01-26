package uz.learn.it.service;

import uz.learn.it.entity.Account;
import uz.learn.it.dto.request.AccountCreationRequestDTO;

import java.util.List;

public interface AccountService {
    void createAccount(AccountCreationRequestDTO accountCreationRequestDTO);

    List<Account> getAccountsByClientId(long id);

    List<Account> getAccounts();
}
