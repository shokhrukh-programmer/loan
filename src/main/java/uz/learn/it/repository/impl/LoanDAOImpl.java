package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.entity.Loan;
import uz.learn.it.repository.LoanDAO;

import java.util.List;

@Repository
public class LoanDAOImpl implements LoanDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public LoanDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Loan> getLoans() {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Loan ", Loan.class)
                .getResultList();

    }

    @Override
    @Transactional
    public void saveLoan(Loan loan) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(loan);
    }

    @Override
    @Transactional
    public Loan findLoanByLoanId(long loanId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Loan where id =: loanId", Loan.class)
                .setParameter("loanId", loanId)
                .getSingleResult();
    }
}
