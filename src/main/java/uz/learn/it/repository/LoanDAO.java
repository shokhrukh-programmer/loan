package uz.learn.it.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Loan;

import java.util.List;

@Repository
public class LoanDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public LoanDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveLoan(Loan loan) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(loan);
            session.getTransaction().commit();
        } catch(Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }

    public List<Loan> getLoans() {
        Query<Loan> query = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from Loan", Loan.class);
            session.getTransaction().commit();
        } catch(Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
            e.printStackTrace();
        }

        return query.getResultList();
    }

    public Loan getLoanByLoanId(long loanId) {
        Loan loan = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            loan = (Loan) session.get(Loan.class, loanId);
            session.getTransaction().commit();
        } catch(Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return loan;
    }
}
