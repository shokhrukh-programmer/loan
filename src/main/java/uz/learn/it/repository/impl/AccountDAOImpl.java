package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Account> cq = cb.createQuery(Account.class);

        Root<Account> root = cq.from(Account.class);

        cq.select(root).where(cb.equal(root.get("client").get("id"), clientId));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public List<Account> getAccounts() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Account> cq = cb.createQuery(Account.class);

        Root<Account> root = cq.from(Account.class);

        cq.select(root);

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Account> cq = cb.createQuery(Account.class);

            Root<Account> root = cq.from(Account.class);

            cq.select(root).where(cb.equal(root.get("accountNumber"), accountNumber));

            Account account = entityManager.createQuery(cq).getSingleResult();

            return Optional.of(account);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> getAccountByAccountId(long accountId) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Account> cq = cb.createQuery(Account.class);

            Root<Account> root = cq.from(Account.class);

            cq.select(root).where(cb.equal(root.get("id"), accountId));

            Account account = entityManager.createQuery(cq).getSingleResult();

            return Optional.of(account);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void updateAccount(Account account) {
        entityManager.merge(account);
    }
}