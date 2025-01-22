package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientDAO clientDAO;

    private final UserDAO userDAO;

    @Autowired
    public ClientServiceImpl(ClientDAO clientDAO, UserDAO userDAO) {
        this.clientDAO = clientDAO;

        this.userDAO = userDAO;
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

        clientDAO.saveClient(client);

        return saveUsernameAndPassword(client.getPhoneNumber(), client.getId());
    }

    @Override
    public void updateClientById(long clientId, ClientModificationRequestDTO tempClient) {
        Client client = clientDAO.findClientByClientId(clientId);

        if(isNotNullAndBlank(tempClient.getFirstName())) {
            client.setFirstName(tempClient.getFirstName());
        }

        if(isNotNullAndBlank(tempClient.getLastName())) {
            client.setLastName(tempClient.getLastName());
        }

        if(isNotNullAndBlank(tempClient.getPassportInfo())) {
            client.setPassportInfo(tempClient.getPassportInfo());
        }

        if(isNotNullAndBlank(tempClient.getPhoneNumber())) {
            client.setPhoneNumber(tempClient.getPhoneNumber());
        }

        if(isNotNullAndBlank(tempClient.getRole())) {
            client.setRole(tempClient.getRole());
        }
    }

    private boolean isNotNullAndBlank(String input) {
        return input != null && !input.isBlank() && !(input
                .replaceAll("[\\p{Cf}\\s]+", "").isEmpty());
    }

    @Override
    public List<Client> getClients() {
        return clientDAO.getClients();
    }

    private void checkForClientExistence(ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        boolean clientExists = clientDAO.getClients().stream()
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

        userDAO.saveUserCredentials(
                UserCredential.builder()
                .username(phoneNumber)
                .password(password)
                .clientId(clientId)
                .build()
        );

        return new ClientRegistrationResponseDTO(phoneNumber, password);
    }
}
