package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.entity.Account;
import uz.learn.it.repository.AccountDAO;

import java.util.List;

@Repository
public class AccountDAOImpl implements AccountDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public AccountDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(account);
    }

    @Override
    public Account findAccountById(long accountId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Ac a where a.id =: accountId", Account.class)
                .setParameter("accountId", accountId)
                .getSingleResult();
    }

    @Override
    public List<Account> getAccountsByClientId(long clientId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Account a where a.clientId =: clientId", Account.class)
                .setParameter("clientId", clientId)
                .getResultList();
    }

    @Override
    public Account findAccountByAccountNumber(String accountNumber) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Account a where a.accountNumber =: accountNumber", Account.class)
                .setParameter("accountNumber", accountNumber)
                .getSingleResult();
    }

    @Override
    public List<Account> getAccounts() {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Account", Account.class)
                .getResultList();
    }
}
