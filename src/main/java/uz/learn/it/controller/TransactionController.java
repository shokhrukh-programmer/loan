package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/histories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<TransactionHistory>>> getOperationHistory() {
        return new ResponseEntity<>(
                APIResponseDTO.<List<TransactionHistory>>builder()
                        .data(transactionService.getOperationHistory())
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping(value = "/{accountId:\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
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
