package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Account;
import uz.learn.it.repository.AccountDAO;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountDAOImpl implements AccountDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveAccount(Account account) {
        entityManager.persist(account);
    }

    @Override
    public List<Account> getAccountsByClientId(long clientId) {
        return entityManager.createQuery("from Account where client.id = :clientId", Account.class)
                    .setParameter("clientId", clientId)
                .getResultList();
    }

    @Override
    public List<Account> getAccounts() {
        return entityManager.createQuery("from Account", Account.class).getResultList();
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        Account account = entityManager.createQuery("from Account where accountNumber = :accountNumber", Account.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();

        return Optional.ofNullable(account);
    }

    @Override
    public Optional<Account> getAccountByAccountId(long accountId) {
        Account account = entityManager.find(Account.class, accountId);

        return Optional.ofNullable(account);
    }

    @Override
    public void updateAccount(Account account) {
        entityManager.merge(account);
    }
}