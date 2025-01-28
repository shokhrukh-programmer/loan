package uz.learn.it.service;

import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getClients();

    void updateClientById(long clientId, ClientModificationRequestDTO client);
}
