package uz.learn.it.service;

import uz.learn.it.dto.UserCredentials;

import java.util.List;

public interface UserService {
    List<UserCredentials> getUserCredentials();
}
