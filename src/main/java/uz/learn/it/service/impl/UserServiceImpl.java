package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.UserCredentialDAO;
import uz.learn.it.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserCredentialDAO userCredentialDAO;

    @Autowired
    public UserServiceImpl(UserCredentialDAO userCredentialDAO) {
        this.userCredentialDAO = userCredentialDAO;
    }

    @Override
    public List<UserCredential> getUserCredentials() {
        return userCredentialDAO.findAll();
    }

    @Override
    public ClientRegistrationResponseDTO saveUsernameAndPassword(String phoneNumber, long clientId) {
        String password = PasswordGenerator.generatePassword();

        userCredentialDAO.save(UserCredential.builder()
                .username(phoneNumber)
                .password(password)
                .clientId(clientId)
                .build()
        );

        return new ClientRegistrationResponseDTO(phoneNumber, password);
    }
}
