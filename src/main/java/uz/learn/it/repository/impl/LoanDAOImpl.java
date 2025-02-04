package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;
import uz.learn.it.repository.LoanDAO;

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
        return entityManager.createQuery("from Loan", Loan.class)
                .getResultList();
    }

    @Override
    public Optional<Loan> getLoanByLoanId(long loanId) {
        Loan loan = entityManager.find(Loan.class, loanId);

        return Optional.ofNullable(loan);
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistory() {
        return entityManager.createQuery("from LoanPaymentHistory", LoanPaymentHistory.class)
                .getResultList();
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistoryByLoanId(long loanId) {
        return entityManager.createQuery("from LoanPaymentHistory l where loan.id =: loanId", LoanPaymentHistory.class)
                .setParameter("loanId", loanId)
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
