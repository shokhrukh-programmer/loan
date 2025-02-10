package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.AuthenticationResponse;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.service.impl.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<APIResponseDTO<ClientRegistrationResponseDTO>> signup(@Valid @RequestBody
                                                                                ClientRegistrationRequestDTO signUpRequest) {
        return new ResponseEntity<>(APIResponseDTO.<ClientRegistrationResponseDTO>builder()
                .data(authenticationService.signup(signUpRequest))
                .build(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO<AuthenticationResponse>> login(@Valid @RequestBody SignInRequest signInRequest) {
        return new ResponseEntity<>(APIResponseDTO.<AuthenticationResponse>builder()
                .data(authenticationService.login(signInRequest)).build(), HttpStatus.OK);
    }
}
