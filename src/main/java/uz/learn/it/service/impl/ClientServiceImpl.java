package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private static int clientId = 1;
    private static int userCredentialId = 1;
    private final Storage storage;

    @Autowired
    public ClientServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public ClientRegistrationResponseDTO registerClient(ClientRegistrationRequestDTO tempClient) {
        checkForClientExistence(tempClient);

        Client client = new Client(
                clientId++,
                tempClient.getFirstName(),
                tempClient.getLastName(),
                tempClient.getPassportInfo(),
                tempClient.getPhoneNumber(),
                tempClient.getRole()
        );

        storage.addClient(client);

        UsernameAndPassword user = saveUsernameAndPassword(client);

        return new ClientRegistrationResponseDTO(user.username, user.password);
    }

    @Override
    public String updateClientById(int id, ClientModificationRequestDTO tempClient) {
        Client client = getClientById(id);

        if(isNotNullOrBlank(tempClient.getFirstName())) {
            client.setFirstName(tempClient.getFirstName());
        }

        if(isNotNullOrBlank(tempClient.getLastName())) {
            client.setLastName(tempClient.getLastName());
        }

        if(isNotNullOrBlank(tempClient.getPassportInfo())) {
            client.setPassportInfo(tempClient.getPassportInfo());
        }

        if(isNotNullOrBlank(tempClient.getPhoneNumber())) {
            client.setPhoneNumber(tempClient.getPhoneNumber());
        }

        if(isNotNullOrBlank(tempClient.getRole())) {
            client.setRole(tempClient.getRole());
        }

        return "Client has successfully been updated";
    }

    private boolean isNotNullOrBlank(String input) {
        return input != null && !input.isBlank();
    }

    @Override
    public List<Client> getClients() {
        return storage.getClients();
    }

    @Override
    public Client getClientById(int id) {
        return storage.getClients().stream().filter(client -> id == client.getId()).findFirst()
                .orElseThrow(() -> new NotFoundException("There is no client with this id!"));
    }

    private void checkForClientExistence(ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        Client c = storage.getClients().stream().filter(client -> client.getPassportInfo().
                equals(clientRegistrationRequestDTO.getPassportInfo()) ||
                client.getPhoneNumber().equals(clientRegistrationRequestDTO.getPhoneNumber()))
                .findFirst().orElse(null);

        if(c != null) {
            throw new AlreadyExistException("This client has already registered!");
        }
    }

    private UsernameAndPassword saveUsernameAndPassword(Client client) {
        String username = client.getPhoneNumber();
        String password = PasswordGenerator.generatePassword();

        storage.addUserLoginDetails(new UserCredentials(userCredentialId++, username, password, client.getId()));
        return new UsernameAndPassword(username, password);
    }

    private static class UsernameAndPassword {
        public final String username;
        public final String password;

        public UsernameAndPassword(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
