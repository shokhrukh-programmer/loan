package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.AccountCreationResponseDTO;
import uz.learn.it.dto.response.AccountResponseDTO;
import uz.learn.it.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<AccountResponseDTO>>> getAccounts() {
        return new ResponseEntity<>(
                APIResponseDTO.<List<AccountResponseDTO>>builder()
                        .data(accountService.getAccounts()).build(), HttpStatus.OK
        );
    }

    @GetMapping(value = "/{clientId:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<AccountResponseDTO>>> getAccountByClientId(
            @PathVariable("clientId") long clientId) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<AccountResponseDTO>>builder()
                        .data(accountService.getAccountsByClientId(clientId)).build(), HttpStatus.OK
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<AccountCreationResponseDTO>> createAccount(
            @Valid @RequestBody AccountCreationRequestDTO accountCreationRequestDTO) {
        accountService.createAccount(accountCreationRequestDTO);

        return new ResponseEntity<>(
                APIResponseDTO.<AccountCreationResponseDTO>builder()
                        .message(SuccessfulMessageConstants.ACCOUNT_OPENED_SUCCESSFULLY_MESSAGE)
                        .build(), HttpStatus.OK
        );
    }
}
