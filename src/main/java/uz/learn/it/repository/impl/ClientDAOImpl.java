package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Client;
import uz.learn.it.repository.ClientDAO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ClientDAOImpl implements ClientDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ClientDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Client> getClients() {
        Session session = sessionFactory.openSession();
        List<Client> clientList = session
                .createQuery("from uz.learn.it.entity.Client", Client.class)
                .getResultList();

        if (clientList == null || clientList.isEmpty()) {
            return new ArrayList<>();  // Return an empty list if no clients are found
        }

        session.close();

        return clientList;

    }

    @Override
    public void saveClient(Client client) {
        Session session = sessionFactory.openSession();

        session.persist(sessionFactory);
        session.close();
    }

    @Override
    public Client findClientByClientId(long clientId) {
        Session session = sessionFactory.getCurrentSession();

        return session
                .createQuery("from Client where id =: clientId", Client.class)
                .setParameter("clientId", clientId)
                .getSingleResult();
    }
}
