package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.UserCredential;

import java.util.List;

@Repository
public interface UserCredentialDAO extends JpaRepository<UserCredential, Long> {
//    List<UserCredential> findAll();
//
//    void save(UserCredential userCredential);
}
