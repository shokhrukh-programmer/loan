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
@RequestMapping("/api")
@Validated
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Account>>> getAccounts() {
        APIResponseDTO<List<Account>> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setData(
                accountService.getAccounts()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/accounts/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Account>>>
    getAccountById(@PathVariable("id") int id) {
        APIResponseDTO<List<Account>> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setData(accountService.getAccountsByClientId(id));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/histories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<TransactionHistory>>> getOperationHistory() {
        APIResponseDTO<List<TransactionHistory>> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setData(accountService.getOperationHistory());

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/accounts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<AccountCreationResponseDTO>> createAccount(
            @Valid @RequestBody AccountCreationRequestDTO accountCreationRequestDTO) {
        APIResponseDTO<AccountCreationResponseDTO> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setMessage("Account has successfully opened!");
        apiResponseDTO.setData(accountService.createAccount(accountCreationRequestDTO));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/accounts/{accountId}/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> doTransaction(
            @PathVariable("accountId") int id, @Valid @RequestBody AccountTransactionRequestDTO accountTransactionRequestDTO) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setMessage(accountService.doTransaction(id, accountTransactionRequestDTO));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
