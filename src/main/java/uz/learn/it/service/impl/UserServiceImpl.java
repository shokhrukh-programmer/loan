package uz.learn.it.service.impl;

import org.springframework.stereotype.Service;
import uz.learn.it.dto.UserCredentials;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<UserCredentials> getUserCredentials() {
        return Storage.getUserCredentials();
    }
}
