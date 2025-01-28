package uz.learn.it.service;

import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse signUp(ClientRegistrationRequestDTO request);

    AuthenticationResponse signIn(SignInRequest request);
}
