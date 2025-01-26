package uz.learn.it.service;

import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;

import java.util.List;

public interface ClientService {
    List<Client> getClients();

    ClientRegistrationResponseDTO registerClient(ClientRegistrationRequestDTO client);

    void updateClientById(long clientId, ClientModificationRequestDTO client);
}
