package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.helper.CriteriaQueryUtil;
import uz.learn.it.repository.TransactionDAO;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TransactionDAOImpl implements TransactionDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<TransactionHistory> getTransactionHistory(int page, int size, LocalDate fromDate, LocalDate toDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<TransactionHistory> cq = cb.createQuery(TransactionHistory.class);

        Root<TransactionHistory> root = cq.from(TransactionHistory.class);

        List<Predicate> predicates = CriteriaQueryUtil.buildDatePredicates(cb, root, "date", fromDate, toDate);

        cq.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public void saveTransaction(TransactionHistory transactionHistory) {
        entityManager.persist(transactionHistory);
    }
}
