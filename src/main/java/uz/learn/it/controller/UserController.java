package uz.learn.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.learn.it.entity.UserCredential;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<APIResponseDTO<List<UserCredential>>> getUserDetails() {
        APIResponseDTO<List<UserCredential>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(
                userService.getUserCredentials()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
