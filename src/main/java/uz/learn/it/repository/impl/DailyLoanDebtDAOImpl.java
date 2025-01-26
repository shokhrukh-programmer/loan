package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.repository.DailyLoanDebtDAO;

import java.util.List;

@Repository
public class DailyLoanDebtDAOImpl implements DailyLoanDebtDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public DailyLoanDebtDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveDailyLoanDebt(DailyLoanPaymentDebt debt) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(debt);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }
    }

    @Override
    public List<DailyLoanPaymentDebt> getDailyLoanDebtsByLoanId(long loanId) {
        List<DailyLoanPaymentDebt> dailyLoanPaymentDebts = null;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            dailyLoanPaymentDebts = session.createQuery("from DailyLoanPaymentDebt where loanId = :loanId",
                    DailyLoanPaymentDebt.class)
                    .setParameter("loanId", loanId)
                    .getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return dailyLoanPaymentDebts;
    }
}
