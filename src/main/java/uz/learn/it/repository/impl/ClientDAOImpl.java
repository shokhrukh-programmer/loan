package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.repository.ClientDAO;

import java.util.List;
import java.util.Optional;

@Repository
public class ClientDAOImpl implements ClientDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Client> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Client> cq = cb.createQuery(Client.class);

        Root<Client> root = cq.from(Client.class);

        cq.select(root);

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public void save(Client client) {
        entityManager.persist(client);
    }

    @Override
    public Optional<Client> getClientById(long clientId) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            CriteriaQuery<Client> cq = cb.createQuery(Client.class);

            Root<Client> root = cq.from(Client.class);

            cq.select(root).where(cb.equal(root.get("id"), clientId));

            Client client = entityManager.createQuery(cq).getSingleResult();

            return Optional.of(client);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(long clientId, ClientModificationRequestDTO tempClient) {
        Client client = entityManager.find(Client.class, clientId);

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
            entityManager.merge(client);
        }
    }
}
