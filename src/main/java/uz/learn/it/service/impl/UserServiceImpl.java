package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.repository.UserCredentialDAO;
import uz.learn.it.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserCredentialDAO userCredentialDAO;

    private final ClientDAO clientDAO;

    @Autowired
    public UserServiceImpl(UserCredentialDAO userCredentialDAO, ClientDAO clientDAO) {
        this.userCredentialDAO = userCredentialDAO;

        this.clientDAO = clientDAO;
    }

    @Override
    public List<UserCredential> getUserCredentials() {
        return userCredentialDAO.findAll();
    }

    @Override
    public ClientRegistrationResponseDTO saveUsernameAndPassword(String phoneNumber, long clientId) {
        String password = PasswordGenerator.generatePassword();

        Client client = clientDAO.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        userCredentialDAO.save(UserCredential.builder()
                .username(phoneNumber)
                .password(password)
                .client(client)
                .build()
        );

        return new ClientRegistrationResponseDTO(phoneNumber, password);
    }
}
