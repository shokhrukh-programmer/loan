package uz.learn.it.repository.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.repository.ClientDAO;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDAOImpl implements ClientDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ClientDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Client> findAll() {
        Session session = sessionFactory.openSession();

        return session.createQuery("from Client", Client.class).getResultList();
    }

    @Override
    public void save(Client client) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.saveOrUpdate(client);

            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }

    @Override
    public Optional<Client> getClientById(long clientId) {
        Session session = sessionFactory.openSession();

        Client client = session.createQuery("from Client where id = :clientId", Client.class)
                .setParameter("clientId", clientId)
                .getSingleResult();

        return Optional.ofNullable(client);
    }

    @Override
    public void update(long clientId, ClientModificationRequestDTO tempClient) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Client client = session.get(Client.class, clientId);

            // Perform the merge
            if (client != null) {
                // Manually update only the required fields
                if (tempClient.getFirstName() != null) {
                    client.setFirstName(tempClient.getFirstName());
                }

                if (tempClient.getLastName() != null) {
                    client.setLastName(tempClient.getLastName());
                }

                if (tempClient.getPassportInfo() != null) {
                    client.setPassportInfo(tempClient.getPassportInfo());
                }

                if (tempClient.getPhoneNumber() != null) {
                    client.setPhoneNumber(tempClient.getPhoneNumber());
                }

                if (tempClient.getRole() != null) {
                    client.setRole(tempClient.getRole());
                }

                // Merge the entity to persist the changes
                session.merge(client);

                session.getTransaction().commit();// Only modified fields are updated
            }
        } catch (Exception e) {
            if (sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }
    }
}
