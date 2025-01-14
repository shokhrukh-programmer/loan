package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.UserCredentials;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final Storage storage;

    @Autowired
    public UserServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<UserCredentials> getUserCredentials() {
        return storage.getUserCredentials();
    }
}
