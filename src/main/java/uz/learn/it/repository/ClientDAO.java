package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Client;

import java.util.Optional;

@Repository
public interface ClientDAO extends JpaRepository<Client, Long> {
    Optional<Client> getClientById(long clientId);

    boolean existsClientByPhoneNumberOrPassportInfo(String phoneNumber, String passportInfo);
}
