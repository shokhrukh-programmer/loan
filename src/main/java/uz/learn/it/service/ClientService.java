package uz.learn.it.service;

import uz.learn.it.dto.Client;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;

import java.util.List;

public interface ClientService {
    Client getClientById(long clientId);

    List<uz.learn.it.entity.Client> getClients();

    ClientRegistrationResponseDTO registerClient(ClientRegistrationRequestDTO client);

    void updateClientById(long clientId, ClientModificationRequestDTO client);
}
