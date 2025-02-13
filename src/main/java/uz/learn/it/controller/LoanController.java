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
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.dto.response.DailyLoanPaymentDebtResponseDTO;
import uz.learn.it.dto.response.LoanPaymentHistoryResponseDTO;
import uz.learn.it.dto.response.LoanResponseDTO;
import uz.learn.it.service.LoanService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping
    public ResponseEntity<APIResponseDTO<List<LoanResponseDTO>>> getLoans(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanResponseDTO>>builder()
                        .data(loanService.getLoans(page, size))
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping(value = "{clientId:\\d+}")
    public ResponseEntity<APIResponseDTO<List<LoanResponseDTO>>> getLoansByClientId(@PathVariable("clientId") long clientId,
                                                                   HttpServletRequest request,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanResponseDTO>>builder()
                        .data(loanService.getLoansByClientId(clientId, request, page, size))
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping(value = "/{loanId:\\d+}/daily-loan-debt")
    public ResponseEntity<APIResponseDTO<List<DailyLoanPaymentDebtResponseDTO>>> getDailyInterest(
            @PathVariable("loanId") long loanId, @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            HttpServletRequest request) throws AccessDeniedException {
        return new ResponseEntity<>(
                APIResponseDTO.<List<DailyLoanPaymentDebtResponseDTO>>builder()
                        .data(loanService.getDailyPaymentsById(loanId, page, size, from, to, request))
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @GetMapping(value = "/payments")
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

    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping(value = "/payments/{clientId:\\d+}")
    public ResponseEntity<APIResponseDTO<List<LoanPaymentHistoryResponseDTO>>> getPaymentsByClientId(
            @PathVariable("clientId") long clientId, HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws AccessDeniedException {
        return new ResponseEntity<>(
                APIResponseDTO.<List<LoanPaymentHistoryResponseDTO>>builder()
                        .data(loanService.getLoanPaymentHistoryByClientId(clientId, request, page, size))
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping
    public ResponseEntity<APIResponseDTO<String>> createLoan(
            @Valid @RequestBody LoanCreationRequestDTO loan) {
        loanService.createLoan(loan);

        return new ResponseEntity<>(
                APIResponseDTO.<String>builder()
                        .message(SuccessfulMessageConstants.SUCCESSFUL_MESSAGE)
                        .build(), HttpStatus.OK
        );
    }

    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    @PostMapping(value = "/{loanId:\\d+}/payments")
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
