package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.repository.DailyLoanPaymentDAO;

import java.util.List;

@Repository
public class DailyLoanPaymentDAOImpl implements DailyLoanPaymentDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public DailyLoanPaymentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void saveDailyPayment(DailyLoanPaymentDebt dailyLoanPaymentDebt) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(dailyLoanPaymentDebt);
    }

    @Override
    @Transactional
    public List<DailyLoanPaymentDebt> getDailyPaymentsByLoanId(long loanId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from DailyLoanPaymentDebt where loanId =: loanId", DailyLoanPaymentDebt.class)
                .setParameter("loanId", loanId)
                .getResultList();
    }
}
