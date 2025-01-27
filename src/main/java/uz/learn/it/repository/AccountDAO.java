package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Account;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountDAO extends JpaRepository<Account, Long> {
    List<Account> getAccountsByClient_Id(long clientId);

    Optional<Account> getAccountsByAccountNumber(String accountNumber);

    Optional<Account> getAccountsById(long id);

    boolean existsAccountByClient_IdAndAccountType(long clientId, String accountType);
}
