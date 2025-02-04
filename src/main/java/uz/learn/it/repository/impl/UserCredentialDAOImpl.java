package uz.learn.it.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
        return entityManager.createQuery("from UserCredential", UserCredential.class)
                .getResultList();
    }

    @Override
    public void save(UserCredential userCredential) {
        entityManager.persist(userCredential);
    }
}
