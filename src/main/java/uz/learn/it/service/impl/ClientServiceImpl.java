package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.entity.Client;
import uz.learn.it.dto.UserCredentials;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientDAO clientDAO;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    @Override
    public ClientRegistrationResponseDTO registerClient(ClientRegistrationRequestDTO tempClient) {
        //checkForClientExistence(tempClient);

        Client client = Client.builder()
                .firstName(tempClient.getFirstName())
                .lastName(tempClient.getLastName())
                .passportInfo(tempClient.getPassportInfo())
                .phoneNumber(tempClient.getPhoneNumber())
                .role(tempClient.getRole())
                .build();

        clientDAO.save(client);

        return saveUsernameAndPassword(client.getPhoneNumber(), client.getId());
    }

    @Override
    public void updateClientById(Client client) {
        clientDAO.update(client);
    }



    @Override
    public List<Client> getClients() {
        return clientDAO.findAll();
    }

    @Override
    public Client getClientById(long clientId) {
        return clientDAO.getClientById(clientId);
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


    private ClientRegistrationResponseDTO saveUsernameAndPassword(String phoneNumber, Long clientId) {
        String password = PasswordGenerator.generatePassword();

        Storage.addUserLoginDetails(
                UserCredentials.builder()
                        .username(phoneNumber)
                        .password(password)
                        .clientId(clientId)
                        .build()
        );

        return new ClientRegistrationResponseDTO(phoneNumber, password);
    }
}
