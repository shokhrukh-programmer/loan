package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Account;
import uz.learn.it.repository.AccountDAO;

import java.util.List;
import java.util.Optional;

@Repository
public class AccountDAOImpl implements AccountDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public AccountDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
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

    @Override
    public List<Account> getAccountsByClientId(long clientId) {
        List<Account> accounts;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query<Account> query = session.createQuery("from Account where clientId = :clientId", Account.class)
                    .setParameter("clientId", clientId);
            accounts = query.getResultList();

            session.getTransaction().commit();
        }

        return accounts;
    }

    @Override
    public List<Account> getAccounts() {
        List<Account> accounts = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            accounts = session.createQuery("from Account", Account.class).getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return accounts;
    }

    @Override
    public Optional<Account> getAccountByAccountNumber(String accountNumber) {
        Account account = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            account = session.createQuery("from Account where accountNumber = :accountNumber", Account.class)
                    .setParameter("accountNumber", accountNumber)
                    .getSingleResult();

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return Optional.ofNullable(account);
    }

    @Override
    public Optional<Account> getAccountByAccountId(long accountId) {
        Account account = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            account = session.get(Account.class, accountId);

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return Optional.ofNullable(account);
    }

    @Override
    public void updateAccount(Account account) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Merge the entity to persist the changes
            session.merge(account);

            session.getTransaction().commit();// Only modified fields are updated
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }
}
