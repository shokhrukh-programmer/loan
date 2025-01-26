package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.repository.UserCredentialDAO;

import java.util.List;

@Repository
public class UserCredentialDAOImpl implements UserCredentialDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserCredentialDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserCredential> findAll() {
        List<UserCredential> userCredentials = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            userCredentials = session.createQuery("from UserCredential", UserCredential.class)
                    .getResultList();

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return userCredentials;
    }

    @Override
    public void save(UserCredential userCredential) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(userCredential);

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }
}
