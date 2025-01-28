package uz.learn.it.service;

import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.request.SignUpRequest;
import uz.learn.it.dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest request);
    AuthenticationResponse signIn(SignInRequest request);
}
