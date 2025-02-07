package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.TransactionHistoryResponseDTO;
import uz.learn.it.service.TransactionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/LoanManagement/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

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
