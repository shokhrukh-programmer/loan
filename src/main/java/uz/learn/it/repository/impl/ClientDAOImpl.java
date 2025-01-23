package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.config.SessionFactoryProvider;
import uz.learn.it.entity.Client;
import uz.learn.it.repository.ClientDAO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO {
    private SessionFactory sessionFactory = SessionFactoryProvider.provideSessionFactory();

//    @Autowired
//    public ClientDAOImpl(SessionFactory sessionFactory) {
//        this.sessionFactory = sessionFactory;
//    }

    @Override
    @Transactional
    public List<Client> getClients() {
        Session session = null;
        Transaction tx = null;
        List<Client> clients = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            clients = session
                    .createQuery("from uz.learn.it.entity.Client", Client.class)
                    .getResultList();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }

        return clients;
    }

    @Override
    @Transactional
    public void saveClient(Client client) {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.saveOrUpdate(client);
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    @Transactional
    public Client findClientByClientId(long clientId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from uz.learn.it.entity.Client where id =: clientId", Client.class)
                .setParameter("clientId", clientId)
                .getSingleResult();
    }
}
