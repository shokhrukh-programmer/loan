package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;
import uz.learn.it.helper.CriteriaQueryUtil;
import uz.learn.it.repository.LoanDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class LoanDAOImpl implements LoanDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveLoan(Loan loan) {
        entityManager.persist(loan);
    }

    @Override
    public List<Loan> getLoans() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Loan> cq = cb.createQuery(Loan.class);

        Root<Loan> root = cq.from(Loan.class);

        cq.select(root);

        return entityManager.createQuery(cq)
                .getResultList();
    }

    @Override
    public Optional<Loan> getLoanByLoanId(long loanId) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Loan> cq = cb.createQuery(Loan.class);

            Root<Loan> root = cq.from(Loan.class);

            cq.select(root).where(cb.equal(root.get("id"), loanId));

            Loan loan = entityManager.createQuery(cq).getSingleResult();

            return Optional.of(loan);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistory(int page, int size, LocalDate fromDate, LocalDate toDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<LoanPaymentHistory> cq = cb.createQuery(LoanPaymentHistory.class);

        Root<LoanPaymentHistory> root = cq.from(LoanPaymentHistory.class);

        List<Predicate> predicates = CriteriaQueryUtil.buildDatePredicates(cb, root, "date", fromDate, toDate);

        cq.select(root).where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(cq)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistoryByLoanId(long loanId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<LoanPaymentHistory> cq = cb.createQuery(LoanPaymentHistory.class);

        Root<LoanPaymentHistory> root = cq.from(LoanPaymentHistory.class);

        cq.select(root).where(cb.equal(root.get("loan").get("id"), loanId));

        return entityManager.createQuery(cq)
                .getResultList();
    }

    @Override
    public void update(Loan loan) {
        entityManager.merge(loan);
    }

    @Override
    public void saveLoanPaymentHistory(LoanPaymentHistory loanPaymentHistory) {
        entityManager.persist(loanPaymentHistory);
    }
}
