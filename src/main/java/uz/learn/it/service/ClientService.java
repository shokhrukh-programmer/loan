package uz.learn.it.service;

import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> getClients(int page, int size);

    void updateClientById(long clientId, ClientModificationRequestDTO client);

    ClientRegistrationResponseDTO resetPassword(long clientId);
}
