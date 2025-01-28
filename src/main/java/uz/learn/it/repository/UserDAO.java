package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.learn.it.entity.User;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
