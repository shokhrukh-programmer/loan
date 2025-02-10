package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.response.UserCredentialResponseDTO;
import uz.learn.it.entity.User;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userCredentialDAO;

    @Autowired
    public UserServiceImpl(UserDAO userCredentialDAO) {
        this.userCredentialDAO = userCredentialDAO;
    }

    @Override
    public List<UserCredentialResponseDTO> getUserCredentials() {
        List<User> userCredentials = userCredentialDAO.findAll();

        return userCredentials.stream()
                .map(u -> new UserCredentialResponseDTO(u.getId(), u.getUsername(),
                        u.getPassword(), u.getClient().getId()))
                .collect(Collectors.toList());
    }
}
