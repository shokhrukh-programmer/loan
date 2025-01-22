package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.repository.Storage;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<UserCredential> getUserCredentials() {
        return userDAO.getUserCredentials();
    }
}
