package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.repository.UserDAO;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void saveUserCredentials(UserCredential userCredential) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(userCredential);
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
