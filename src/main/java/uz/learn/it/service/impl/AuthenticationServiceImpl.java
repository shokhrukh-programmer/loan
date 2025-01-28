package uz.learn.it.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.request.SignUpRequest;
import uz.learn.it.dto.response.AuthenticationResponse;
import uz.learn.it.entity.User;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.AuthenticationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        var user = userDAO.save(
                User.builder()
                        .username(request.getUsername())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(request.getRole())
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
