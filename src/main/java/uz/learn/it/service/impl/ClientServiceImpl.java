package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.User;
import uz.learn.it.exception.NotFoundException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.ClientService;
import uz.learn.it.specification.ClientSpecification;

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
    @Transactional
    public void updateClientById(long clientId, ClientModificationRequestDTO tempClient) {
        Client client = clientDAO.getClientById(clientId).orElseThrow(NotFoundException::new);

        if (validateInput(tempClient.getFirstName())) {
            client.setFirstName(tempClient.getFirstName());
        }

        if (validateInput(tempClient.getLastName())) {
            client.setLastName(tempClient.getLastName());
        }

        if (validateInput(tempClient.getPassportInfo())) {
            client.setPassportInfo(tempClient.getPassportInfo());
        }

        if (validateInput(tempClient.getRole().name())) {
            client.setRole(tempClient.getRole());
        }

        if (validateInput(tempClient.getPhoneNumber())) {
            client.setPhoneNumber(tempClient.getPhoneNumber());
        }

        clientDAO.save(client);
    }

    private boolean validateInput(String input) {
        return input != null && !input.isBlank();
    }

    @Override
    public List<Client> getClients(int page, int size) {
        Specification<Client> specification = Specification.where(ClientSpecification.clients());
        Page<Client> clientPage = clientDAO.findAll(specification, PageRequest.of(page, size));

        return clientPage.getContent();
    }

    @Override
    public ClientRegistrationResponseDTO resetPassword(long clientId) {
        User user = userDAO.getUserByClientId(clientId).orElseThrow(ClientNotFoundException::new);

        String newPassword = PasswordGenerator.generatePassword();

        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));

        userDAO.save(user);

        return new ClientRegistrationResponseDTO(user.getUsername(), newPassword);
    }
}
