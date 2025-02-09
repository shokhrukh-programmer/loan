package uz.learn.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.response.AuthenticationResponse;
import uz.learn.it.service.impl.AuthenticationService;

@RestController
@RequestMapping("/LoanManagement/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody ClientRegistrationRequestDTO signUpRequest) {
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signin(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }
}
