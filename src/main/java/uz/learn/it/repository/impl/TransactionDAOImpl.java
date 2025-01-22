package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.repository.TransactionDAO;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public TransactionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void saveOperationToHistory(TransactionHistory transactionHistory) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(transactionHistory);
    }
}
