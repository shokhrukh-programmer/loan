package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.repository.TransactionDAO;

import java.util.List;

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

    @Override
    @Transactional
    public List<TransactionHistory> getTransactionHistory() {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from TransactionHistory", TransactionHistory.class)
                .getResultList();
    }

    @Override
    @Transactional
    public void saveDailyPayment(DailyLoanPaymentDebt dailyLoanPaymentDebt) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(dailyLoanPaymentDebt);
    }

    @Override
    @Transactional
    public List<DailyLoanPaymentDebt> getDailyDebtHistory(long loanId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from DailyLoanPaymentDebt where loanId =: loanId", DailyLoanPaymentDebt.class)
                .setParameter("loanId", loanId)
                .getResultList();
    }
}
