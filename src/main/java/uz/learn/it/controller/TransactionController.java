package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping(value = "/histories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<TransactionHistory>>> getOperationHistory() {
        APIResponseDTO<List<TransactionHistory>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(transactionService.getOperationHistory());

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/{accountId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> doTransaction(
            @PathVariable("accountId") int id,
            @Valid @RequestBody AccountTransactionRequestDTO accountTransactionRequestDTO) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setMessage(Constants.TRANSACTION_DONE_SUCCESSFULLY_MESSAGE);

        transactionService.makeTransaction(id, accountTransactionRequestDTO);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
