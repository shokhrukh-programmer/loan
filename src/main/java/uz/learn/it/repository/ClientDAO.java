package uz.learn.it.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Client;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ClientDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public ClientDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Client> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from uz.learn.it.entity.Client", Client.class);

        return query.getResultList();
    }
}
