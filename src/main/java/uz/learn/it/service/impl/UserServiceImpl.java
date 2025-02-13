package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.response.UserCredentialResponseDTO;
import uz.learn.it.entity.User;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.UserService;
import uz.learn.it.specification.UserSpecification;

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
    public List<UserCredentialResponseDTO> getUserCredentials(int page, int size) {
        Specification<User> spec = Specification.where(UserSpecification.getUserSpecification());
        Page<User> userPage = userCredentialDAO.findAll(spec, PageRequest.of(page, size));

        return userPage.getContent().stream()
                .map(u -> new UserCredentialResponseDTO(u.getId(), u.getUsername(),
                        u.getPassword(), u.getClient().getId()))
                .collect(Collectors.toList());
    }
}
