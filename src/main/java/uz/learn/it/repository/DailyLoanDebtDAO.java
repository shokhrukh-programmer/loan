package uz.learn.it.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.DailyLoanPaymentDebt;

import java.util.List;

@Repository
public class DailyLoanDebtDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public DailyLoanDebtDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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

    public List<DailyLoanPaymentDebt> getDailyLoanDebtsByLoanId(long loanId) {
        Query<DailyLoanPaymentDebt> query = null;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from DailyLoanPaymentDebt where loanId = :loanId",
                    DailyLoanPaymentDebt.class);
            query.setParameter("loanId", loanId);
            session.getTransaction().commit();
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return query.getResultList();
    }
}
