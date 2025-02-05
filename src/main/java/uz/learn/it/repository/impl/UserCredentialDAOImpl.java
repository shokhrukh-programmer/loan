package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.repository.UserCredentialDAO;

import java.util.List;

@Repository
public class UserCredentialDAOImpl implements UserCredentialDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserCredential> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserCredential> cq = cb.createQuery(UserCredential.class);

        Root<UserCredential> root = cq.from(UserCredential.class);

        cq.select(root);

        return entityManager.createQuery(cq)
                .getResultList();
    }

    @Override
    public void save(UserCredential userCredential) {
        entityManager.persist(userCredential);
    }
}
