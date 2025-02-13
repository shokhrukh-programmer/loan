package uz.learn.it.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.AccountCreationResponseDTO;
import uz.learn.it.dto.response.AccountResponseDTO;
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

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<APIResponseDTO<List<AccountResponseDTO>>> getAccounts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<AccountResponseDTO>>builder()
                        .data(accountService.getAccounts(page, size)).build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping(value = "/{clientId:\\d+}")
    public ResponseEntity<APIResponseDTO<List<AccountResponseDTO>>> getAccountByClientId(
            @PathVariable("clientId") long clientId, HttpServletRequest request) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<AccountResponseDTO>>builder()
                        .data(accountService.getAccountsByClientId(clientId, request)).build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping
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
