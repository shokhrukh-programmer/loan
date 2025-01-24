package uz.learn.it.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Client;

import java.util.List;

@Repository
public class ClientDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public ClientDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Client> findAll() {
        List<Client> clients = null;

        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            clients = session.createQuery("from Client", Client.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return clients;
    }

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

    public Client getClientById(long clientId) {
        Query<Client> query = null;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            query = session.createQuery("from Client where id = :clientId", Client.class)
                    .setParameter("clientId", clientId);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(sessionFactory.getCurrentSession().getTransaction().isActive()) {
                sessionFactory.getCurrentSession().getTransaction().rollback();
            }

            e.printStackTrace();
        }

        return query.getSingleResult();
    }

    public void update(Client tempClient) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Client client = session.get(Client.class, tempClient.getId());
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
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close(); // Ensure the session is closed
            }
        }
    }
}
