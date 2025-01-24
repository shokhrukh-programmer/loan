package uz.learn.it.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.UserCredential;

import java.util.List;

@Repository
public class UserCredentialDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserCredentialDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<UserCredential> findAll() {
        Query<UserCredential> query = null;

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from UserCredential", UserCredential.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return query.getResultList();
    }
}
