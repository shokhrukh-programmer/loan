package uz.learn.it.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Account;

import java.util.List;

@Repository
public class AccountDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public AccountDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveAccount(Account account) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(account);

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }

    public List<Account> getAccountsByClientId(long clientId) {
        Query<Account> query;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            query = session.createQuery("from Account where clientId = :clientId", Account.class);

            query.setParameter("clientId", clientId);

            session.getTransaction().commit();
        }

        return query.getResultList();
    }

    public List<Account> getAccounts() {
        Query<Account> query = null;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from Account", Account.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return query.getResultList();
    }

    public Account getAccountsByAccountNumber(String accountNumber) {
        Query<Account> query = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from Account where accountNumber = :accountNumber", Account.class);
            query.setParameter("accountNumber", accountNumber);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return query.getSingleResult();
    }

    public Account getAccountById(long accountId) {
        Account account;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            account = session.get(Account.class, accountId);

            session.getTransaction().commit();
        }

        return account;
    }
}
