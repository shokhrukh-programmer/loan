package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.handler.ExceptionHandlingWebHandler;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.repository.UserDAO;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void saveUserCredentials(UserCredential userCredential) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.saveOrUpdate(userCredential);

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    @Transactional
    public List<UserCredential> getUserCredentials() {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from UserCredential", UserCredential.class)
                .getResultList();
    }
}
