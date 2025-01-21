package uz.learn.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.learn.it.dto.UserCredentials;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<UserCredentials>>> getUserDetails() {
        APIResponseDTO<List<UserCredentials>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(
                userService.getUserCredentials()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
