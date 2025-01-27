package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, String> {
    User findByUsername(String username);
}
