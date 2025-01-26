package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.repository.TransactionDAO;

import java.util.List;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public TransactionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TransactionHistory> getTransactionHistory() {
        List<TransactionHistory> transactionHistories = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            transactionHistories = session.createQuery("from TransactionHistory", TransactionHistory.class)
                    .getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return transactionHistories;
    }

    @Override
    public void saveTransaction(TransactionHistory transactionHistory) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(transactionHistory);

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }
}
