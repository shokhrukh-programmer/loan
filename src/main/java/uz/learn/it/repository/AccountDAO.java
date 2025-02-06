package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {
    List<Account> getAccountsByClientId(long clientId);

    List<Account> findByClientId(long clientId);

    Optional<Account> getAccountsByAccountNumber(String accountNumber);

    Optional<Account> getAccountsById(long id);

//    void saveAccount(Account account);
//
//    List<Account> getAccountsByClientId(long clientId);
//
//    List<Account> getAccounts();
//
//    Optional<Account> getAccountByAccountNumber(String accountNumber);
//
//    Optional<Account> getAccountByAccountId(long accountId);
//
//    void updateAccount(Account account);
}
