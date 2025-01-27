package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constant.Constants;
import uz.learn.it.entity.Account;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.AccountCreationResponseDTO;
import uz.learn.it.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<APIResponseDTO<List<Account>>> getAccounts() {
        APIResponseDTO<List<Account>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(
                accountService.getAccounts()
        );

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "{clientId:[0-9]+}")
    public ResponseEntity<APIResponseDTO<List<Account>>> getAccountByClientId(
            @PathVariable long clientId) {
        APIResponseDTO<List<Account>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(accountService.getAccountsByClientId(clientId));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<APIResponseDTO<AccountCreationResponseDTO>> createAccount(
            @RequestBody @Valid AccountCreationRequestDTO accountCreationRequestDTO) {
        APIResponseDTO<AccountCreationResponseDTO> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setMessage(Constants.ACCOUNT_OPENED_SUCCESSFULLY_MESSAGE);
        accountService.createAccount(accountCreationRequestDTO);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
