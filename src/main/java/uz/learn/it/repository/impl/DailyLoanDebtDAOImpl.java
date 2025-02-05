package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.helper.CriteriaQueryUtil;
import uz.learn.it.repository.DailyLoanDebtDAO;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DailyLoanDebtDAOImpl implements DailyLoanDebtDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveDailyLoanDebt(DailyLoanPaymentDebt debt) {
        entityManager.persist(debt);
    }

    @Override
    public List<DailyLoanPaymentDebt> getDailyLoanDebtsByLoanId(long loanId, int page, int size,
                                                                LocalDate fromDate, LocalDate toDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<DailyLoanPaymentDebt> cq = cb.createQuery(DailyLoanPaymentDebt.class);

        Root<DailyLoanPaymentDebt> root = cq.from(DailyLoanPaymentDebt.class);

        List<Predicate> predicates = CriteriaQueryUtil.buildDatePredicates(cb, root, "date", fromDate, toDate);

        predicates.add(cb.equal(root.get("loan").get("id"), loanId));

        cq.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }
}
