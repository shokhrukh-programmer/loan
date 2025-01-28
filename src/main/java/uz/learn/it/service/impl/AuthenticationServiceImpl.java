package uz.learn.it.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.response.AuthenticationResponse;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.User;
import uz.learn.it.enums.Role;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.helper.PasswordGenerator;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.AuthenticationService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDAO userDAO;

    private final ClientDAO clientDAO;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse signUp(ClientRegistrationRequestDTO request) {
        if(clientDAO.existsClientByPhoneNumberOrPassportInfo(request.getPhoneNumber(), request.getPassportInfo())) {
            throw new AlreadyExistException(Constants.CLIENT_ALREADY_EXIST_MESSAGE);
        }

        Client client = Client.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .passportInfo(request.getPassportInfo())
                .phoneNumber(request.getPhoneNumber())
                .build();

        clientDAO.save(client);

        var user = userDAO.save(
                User.builder()
                        .username(request.getPhoneNumber())
                        .password(passwordEncoder.encode(PasswordGenerator.generatePassword()))
                        //.clientId(client.getId())
                        .role(Role.ROLE_USER)
                        .client(client)
                        .build()
        );

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user)).build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        ));

        var user = userDAO.findByUsername(request.getUsername()).orElseThrow();

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user)).build();
    }
}
