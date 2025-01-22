package uz.learn.it.repository;

import uz.learn.it.entity.Client;

import java.util.List;

public interface ClientDAO {
    List<Client> getClients();

    void saveClient(Client client);

    Client findClientByClientId(long clientId);
}
