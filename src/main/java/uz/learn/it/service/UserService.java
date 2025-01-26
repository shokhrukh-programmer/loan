package uz.learn.it.service;

import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.UserCredential;

import java.util.List;

public interface UserService {
    List<UserCredential> getUserCredentials();
    ClientRegistrationResponseDTO saveUsernameAndPassword(String phoneNumber, long clientId);
}
