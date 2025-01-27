package uz.learn.it.repository;

import uz.learn.it.entity.Account;

import java.util.List;
import java.util.Optional;

public interface AccountDAO {
    void saveAccount(Account account);

    List<Account> getAccountsByClientId(long clientId);

    List<Account> getAccounts();

    Optional<Account> getAccountByAccountNumber(String accountNumber);

    Optional<Account> getAccountByAccountId(long accountId);
    
    void updateAccount(Account account);
}
