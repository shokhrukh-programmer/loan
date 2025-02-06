package uz.learn.it.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientDAO extends JpaRepository<Client, Long> {
    Optional<Client> getClientById(long clientId);
//    List<Client> findAll();
//
//    void save(Client client);
//
//    Optional<Client> getClientById(long clientId);
//
//    void update(long clientId, ClientModificationRequestDTO tempClient);
}
