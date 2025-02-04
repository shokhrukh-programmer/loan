package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.repository.TransactionDAO;

import java.util.List;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TransactionHistory> getTransactionHistory() {
        return entityManager.createQuery("from TransactionHistory", TransactionHistory.class)
                .getResultList();
    }

    @Override
    public void saveTransaction(TransactionHistory transactionHistory) {
        entityManager.persist(transactionHistory);
    }
}
