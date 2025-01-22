package uz.learn.it.repository;

import uz.learn.it.entity.UserCredential;

import java.util.List;

public interface UserDAO {
    List<UserCredential> getUserCredentials();

    void saveUserCredentials(UserCredential userCredential);
}
