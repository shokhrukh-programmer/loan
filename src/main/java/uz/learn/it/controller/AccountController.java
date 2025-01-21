package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.dto.Account;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.AccountCreationResponseDTO;
import uz.learn.it.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    private static final String ACCOUNT_OPENED_SUCCESSFULLY_MESSAGE = "Account has successfully opened!";

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Account>>> getAccounts() {
        APIResponseDTO<List<Account>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(
                accountService.getAccounts()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Account>>> getAccountById(
            @PathVariable("accountId") Long accountId) {
        APIResponseDTO<List<Account>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(accountService.getAccountsByClientId(accountId));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<AccountCreationResponseDTO>> createAccount(
            @Valid @RequestBody AccountCreationRequestDTO accountCreationRequestDTO) {
        APIResponseDTO<AccountCreationResponseDTO> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setMessage(ACCOUNT_OPENED_SUCCESSFULLY_MESSAGE);

        apiResponseDTO.setData(accountService.createAccount(accountCreationRequestDTO));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
