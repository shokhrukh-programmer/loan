package uz.learn.it.repository;

import uz.learn.it.entity.Account;

import java.util.List;

public interface AccountDAO {
    List<Account> getAccounts();

    void saveAccount(Account account);

    Account findAccountById(long accountId);

    List<Account> getAccountsByClientId(long clientId);

    Account findAccountByAccountNumber(String accountNumber);
}
