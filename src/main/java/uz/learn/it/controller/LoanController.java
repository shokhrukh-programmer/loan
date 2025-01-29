package uz.learn.it.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constants.SuccessfulMessageConstants;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.entity.LoanPaymentHistory;
import uz.learn.it.service.LoanService;

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
    public ResponseEntity<APIResponseDTO<List<Loan>>> getLoans() {
        return new ResponseEntity<>(
                APIResponseDTO.<List<Loan>>builder()
                        .data(loanService.getLoans())
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping(value = "/{loanId:\\d+}/daily-loan-debt",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<DailyLoanPaymentDebt>>> getDailyInterest(
            @PathVariable("loanId") long loanId) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<DailyLoanPaymentDebt>>builder()
                        .data(loanService.getDailyPaymentsById(loanId))
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<LoanPaymentHistory>>> getPayments() {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanPaymentHistory>>builder()
                        .data(loanService.getLoanPaymentHistory())
                        .build(), HttpStatus.OK
        );
    }

    @GetMapping(value = "/payments/{loanId:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<LoanPaymentHistory>>> getPaymentsByLoanId(
            @PathVariable("loanId") long loanId) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanPaymentHistory>>builder()
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
