package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.controller.AuthenticationController;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.response.AuthenticationResponse;
import uz.learn.it.entity.User;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.UserDAO;

@Service
public class AuthenticationService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(UserDAO userDAO, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse signup(ClientRegistrationRequestDTO request) {
        if(userDAO.existsByUsername(request.getPhoneNumber())) {
            throw new AlreadyExistException(ExceptionMessageConstants.CLIENT_ALREADY_EXIST_MESSAGE);
        }

        var user = userDAO.save(User.builder()
                .username(request.getPhoneNumber())
                .password(passwordEncoder.encode(PasswordGenerator.generatePassword()))
                .role(request.getRole()).build());

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }

    public AuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        var user = userDAO.findByUsername(request.getUsername()).orElseThrow(ClientNotFoundException::new);

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .build();
    }
}
