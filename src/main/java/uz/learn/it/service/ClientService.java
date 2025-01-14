package uz.learn.it.service;

import uz.learn.it.dto.Client;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;

import java.util.List;

public interface ClientService {
    Client getClientById(int id);
    List<Client> getClients();
    ClientRegistrationResponseDTO registerClient(ClientRegistrationRequestDTO client);
    String updateClientById(int id, ClientModificationRequestDTO client);
}
