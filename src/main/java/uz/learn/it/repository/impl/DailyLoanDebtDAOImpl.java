package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.repository.DailyLoanDebtDAO;

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
    public List<DailyLoanPaymentDebt> getDailyLoanDebtsByLoanId(long loanId) {
        return entityManager.createQuery("from DailyLoanPaymentDebt where loan.id = :loanId",
                        DailyLoanPaymentDebt.class)
                .setParameter("loanId", loanId)
                .getResultList();
    }
}
