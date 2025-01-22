package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.entity.Client;
import uz.learn.it.repository.ClientDAO;

import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ClientDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public List<Client> getClients() {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Client", Client.class)
                .getResultList();

    }

    @Override
    @Transactional
    public void saveClient(Client client) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(sessionFactory);
    }

    @Override
    @Transactional
    public Client findClientByClientId(long clientId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Client where id =: clientId", Client.class)
                .setParameter("clientId", clientId)
                .getSingleResult();
    }
}
