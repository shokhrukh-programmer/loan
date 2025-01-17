package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.dto.DailyLoanPaymentDebt;
import uz.learn.it.dto.Loan;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping(value = "/loans", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Loan>>> getLoans() {
        APIResponseDTO<List<Loan>> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setData(loanService.getLoans());

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/payments/{loanId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<DailyLoanPaymentDebt>>> getDailyInterest(@PathVariable("loanId") int loanId) {
        APIResponseDTO<List<DailyLoanPaymentDebt>> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setData(loanService.getDailyPayments(loanId));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/loans", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> createLoan(@Valid @RequestBody LoanCreationRequestDTO loan) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setMessage(loanService.createLoan(loan));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> doPaymentToLoanInterest(@Valid @RequestBody LoanPaymentRequestDTO loan) {
        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();
        apiResponseDTO.setMessage(loanService.payForLoan(loan));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
