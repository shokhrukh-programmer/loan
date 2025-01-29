package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;
import uz.learn.it.repository.LoanDAO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class LoanDAOImpl implements LoanDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public LoanDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
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

    @Override
    public List<Loan> getLoans() {
        List<Loan> loans = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            loans = session.createQuery("from Loan", Loan.class)
                    .getResultList();

            session.getTransaction().commit();
        } catch(Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return loans;
    }

    @Override
    public Optional<Loan> getLoanByLoanId(long loanId) {
        Loan loan = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            loan =  session.get(Loan.class, loanId);

            session.getTransaction().commit();
        } catch(Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return Optional.ofNullable(loan);
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistory() {
        List<LoanPaymentHistory> loanPaymentHistory = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            loanPaymentHistory = session.createQuery("from LoanPaymentHistory", LoanPaymentHistory.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch(Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }

        return loanPaymentHistory;
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistoryByLoanId(long loanId) {
        List<LoanPaymentHistory> loanPaymentHistory = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            loanPaymentHistory = session.createQuery("from LoanPaymentHistory l where loan.id =: loanId", LoanPaymentHistory.class)
                    .setParameter("loanId", loanId)
                    .getResultList();
            session.getTransaction().commit();
        } catch(Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }
        }

        return loanPaymentHistory;
    }

    @Override
    public void update(Loan loan) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Merge the entity to persist the changes
            session.merge(loan);

            session.getTransaction().commit();// Only modified fields are updated
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public void saveLoanPaymentHistory(LoanPaymentHistory loanPaymentHistory) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(loanPaymentHistory);
            session.getTransaction().commit();
        } catch(Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }
}
