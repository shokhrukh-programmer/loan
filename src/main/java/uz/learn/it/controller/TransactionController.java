package uz.learn.it.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.TransactionHistoryResponseDTO;
import uz.learn.it.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping(value = "/histories")
    public ResponseEntity<APIResponseDTO<List<TransactionHistoryResponseDTO>>> getOperationHistory(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<TransactionHistoryResponseDTO>>builder()
                        .data(transactionService.getOperationHistory(page, size, from, to))
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping(value = "/histories/me")
    public ResponseEntity<APIResponseDTO<List<TransactionHistoryResponseDTO>>> getOperationHistoryByClientId(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<TransactionHistoryResponseDTO>>builder()
                        .data(transactionService.getOperationHistoryByClientId(request, page, size, from, to))
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping(value = "/{accountId:\\d+}")
    public ResponseEntity<APIResponseDTO<String>> doTransaction(
            @PathVariable("accountId") long id,
            @Valid @RequestBody AccountTransactionRequestDTO accountTransactionRequestDTO) {
        transactionService.makeTransaction(id, accountTransactionRequestDTO);

        return new ResponseEntity<>(
                APIResponseDTO.<String>builder()
                        .message(SuccessfulMessageConstants.TRANSACTION_DONE_SUCCESSFULLY_MESSAGE)
                        .build(), HttpStatus.OK
        );
    }
}
