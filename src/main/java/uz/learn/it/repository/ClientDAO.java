package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import uz.learn.it.entity.Client;

import java.util.Optional;

@Repository
public interface ClientDAO extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    Optional<Client> getClientById(long clientId);

    boolean existsClientByPassportInfo(String passportInfo);

    boolean existsClientByPhoneNumber(String phoneNumber);
}
