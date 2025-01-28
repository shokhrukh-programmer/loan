package uz.learn.it.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.request.ClientRegistrationRequestDTO;
import uz.learn.it.dto.request.SignInRequest;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.AuthenticationResponse;
import uz.learn.it.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<APIResponseDTO<AuthenticationResponse>> registerClient(
            @RequestBody @Valid ClientRegistrationRequestDTO clientRegistrationRequestDTO) {
        APIResponseDTO<AuthenticationResponse> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setMessage(Constants.CLIENT_REGISTERED_SUCCESSFULLY_MESSAGE);

        apiResponseDTO.setData(
                authenticationService.signUp(clientRegistrationRequestDTO)
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}
