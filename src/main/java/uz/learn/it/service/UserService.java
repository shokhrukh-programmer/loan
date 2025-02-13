package uz.learn.it.service;

import uz.learn.it.dto.response.UserCredentialResponseDTO;

import java.util.List;

public interface UserService {
    List<UserCredentialResponseDTO> getUserCredentials(int page, int size);
}
