package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.response.AuthenticationResponse;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.User;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.repository.UserDAO;

@Service
public class AuthenticationService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ClientDAO clientDAO;

    @Autowired
    public AuthenticationService(UserDAO userDAO, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, ClientDAO clientDAO) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.clientDAO = clientDAO;
    }

    public ClientRegistrationResponseDTO signup(ClientRegistrationRequestDTO request) {
        if (clientDAO.existsClientByPhoneNumber(request.getPhoneNumber()) ||
                clientDAO.existsClientByPassportInfo(request.getPassportInfo())) {
            throw new AlreadyExistException(ExceptionMessageConstants.CLIENT_ALREADY_EXIST_MESSAGE);
        }

        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .passportInfo(request.getPassportInfo())
                .role(request.getRole())
                .build();

        clientDAO.save(client);

        String password = PasswordGenerator.generatePassword();

        userDAO.save(User.builder()
                .username(request.getPhoneNumber())
                .password(passwordEncoder.encode(password))
                .role(request.getRole())
                .client(client)
                .build());

        return ClientRegistrationResponseDTO.builder()
                .username(client.getPhoneNumber())
                .password(password)
                .build();
    }

    public AuthenticationResponse login(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        var user = userDAO.findByUsername(request.getUsername()).orElseThrow(ClientNotFoundException::new);

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user, user.getClient()))
                .build();
    }
}
