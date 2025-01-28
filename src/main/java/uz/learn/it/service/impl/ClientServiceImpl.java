package uz.learn.it.service.impl;

import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.Client;
import uz.learn.it.dto.UserCredentials;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.exception.NotFoundException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.ClientService;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
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

        Storage.addClient(client);

        return saveUsernameAndPassword(client.getPhoneNumber(), client.getId());
    }

    @Override
    public void updateClientById(long id, ClientModificationRequestDTO tempClient) {
        Client client = getClientById(id);

        if (isNotNullAndBlank(tempClient.getFirstName())) {
            client.setFirstName(tempClient.getFirstName());
        }

        if (isNotNullAndBlank(tempClient.getLastName())) {
            client.setLastName(tempClient.getLastName());
        }

        if (isNotNullAndBlank(tempClient.getPassportInfo())) {
            client.setPassportInfo(tempClient.getPassportInfo());
        }

        if (isNotNullAndBlank(tempClient.getPhoneNumber())) {
            client.setPhoneNumber(tempClient.getPhoneNumber());
        }

        if (isNotNullAndBlank(tempClient.getRole())) {
            client.setRole(tempClient.getRole());
        }
    }

    private boolean isNotNullAndBlank(String input) {
        return input != null && !input.isBlank();
    }

    @Override
    public List<Client> getClients() {
        return Storage.getClients();
    }

    @Override
    public Client getClientById(long clientId) {
        return Storage.findClientById(clientId)
                .orElseThrow(() -> new NotFoundException(Constants.CLIENT_NOT_FOUND_MESSAGE));
    }

    private void checkForClientExistence(ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        boolean clientExists = Storage.getClients().stream()
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
