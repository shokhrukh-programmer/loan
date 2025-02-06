package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.DailyLoanPaymentDebtResponseDTO;
import uz.learn.it.dto.response.LoanPaymentHistoryResponseDTO;
import uz.learn.it.dto.response.LoanResponseDTO;
import uz.learn.it.service.LoanService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<LoanResponseDTO>>> getLoans() {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanResponseDTO>>builder()
                        .data(loanService.getLoans())
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping(value = "/{loanId:\\d+}/daily-loan-debt",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<DailyLoanPaymentDebtResponseDTO>>> getDailyInterest(
            @PathVariable("loanId") long loanId, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<DailyLoanPaymentDebtResponseDTO>>builder()
                        .data(loanService.getDailyPaymentsById(loanId, page, size, from, to))
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<LoanPaymentHistoryResponseDTO>>> getPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanPaymentHistoryResponseDTO>>builder()
                        .data(loanService.getLoanPaymentHistory(page, size, from, to))
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping(value = "/payments/{loanId:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<LoanPaymentHistoryResponseDTO>>> getPaymentsByLoanId(
            @PathVariable("loanId") long loanId) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanPaymentHistoryResponseDTO>>builder()
                        .data(loanService.getLoanPaymentHistoryByLoanId(loanId))
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> createLoan(
            @Valid @RequestBody LoanCreationRequestDTO loan) {
        loanService.createLoan(loan);

        return new ResponseEntity<>(
                APIResponseDTO.<String>builder()
                        .message(SuccessfulMessageConstants.SUCCESSFUL_MESSAGE)
                        .build(), HttpStatus.OK
        );
    }

    @PostMapping(value = "/{loanId:\\d+}/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> doPaymentToLoan(
            @PathVariable("loanId") long loanId, @Valid @RequestBody LoanPaymentRequestDTO loan) {
        loanService.payForLoanDebt(loanId, loan);

        return new ResponseEntity<>(
                APIResponseDTO.<String>builder()
                        .message(SuccessfulMessageConstants.PAYMENT_DONE_SUCCESSFULLY_MESSAGE)
                        .build(), HttpStatus.OK
        );
    }
}
