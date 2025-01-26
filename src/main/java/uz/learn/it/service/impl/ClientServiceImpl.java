package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.service.ClientService;
import uz.learn.it.service.UserService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientDAO clientDAO;

    private final UserService userService;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO, UserService userService) {
        this.clientDAO = clientDAO;

        this.userService = userService;
    }

    @Override
    public ClientRegistrationResponseDTO registerClient(ClientRegistrationRequestDTO tempClient) {
        checkForClientExistence(tempClient);

        Client client = Client.builder()
                .firstName(tempClient.getFirstName())
                .lastName(tempClient.getLastName())
                .passportInfo(tempClient.getPassportInfo())
                .phoneNumber(tempClient.getPhoneNumber())
                .role(tempClient.getRole())
                .build();

        clientDAO.save(client);

        return userService.saveUsernameAndPassword(client.getPhoneNumber(), client.getId());
    }

    @Override
    public void updateClientById(long clientId, ClientModificationRequestDTO client) {
        clientDAO.update(clientId, client);
    }

    @Override
    public List<Client> getClients() {
        return clientDAO.findAll();
    }

    private void checkForClientExistence(ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        boolean clientExists = clientDAO.findAll().stream()
                .anyMatch(client -> hasMatchingDetails(client, clientRegistrationRequestDTO));

        if (clientExists) {
            throw new AlreadyExistException(Constants.CLIENT_ALREADY_EXIST_MESSAGE);
        }
    }

    private boolean hasMatchingDetails(Client client, ClientRegistrationRequestDTO dto) {
        return client.getPassportInfo().equals(dto.getPassportInfo()) ||
                client.getPhoneNumber().equals(dto.getPhoneNumber());
    }
}
