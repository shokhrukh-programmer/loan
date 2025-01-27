package uz.learn.it.repository;

import uz.learn.it.entity.UserCredential;

import java.util.List;

public interface UserCredentialDAO {
    List<UserCredential> findAll();

    void save(UserCredential userCredential);
}
