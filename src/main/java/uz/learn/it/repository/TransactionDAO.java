package uz.learn.it.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.TransactionHistory;

import java.util.List;

@Repository
public class TransactionDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public TransactionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<TransactionHistory> getTransactionHistory() {
        Query<TransactionHistory> query = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from TransactionHistory", TransactionHistory.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return query.getResultList();
    }

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
