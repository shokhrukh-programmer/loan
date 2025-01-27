package uz.learn.it.repository;

import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDAO {
    List<Client> findAll();

    void save(Client client);

    Optional<Client> getClientById(long clientId);

    void update(long clientId, ClientModificationRequestDTO tempClient);
}
