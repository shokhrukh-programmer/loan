package uz.learn.it.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.DailyLoanPaymentDebt;
import uz.learn.it.dto.Loan;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.dto.response.APIResponseDTO;
import uz.learn.it.service.LoanService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<Loan>>> getLoans() {
        APIResponseDTO<List<Loan>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(loanService.getLoans());

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{loanId:[0-9]+}/daily-loan-debt",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<List<DailyLoanPaymentDebt>>> getDailyInterest(
            @PathVariable("loanId") int loanId) {
        APIResponseDTO<List<DailyLoanPaymentDebt>> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setData(loanService.getDailyPaymentsById(loanId));

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> createLoan(
            @Valid @RequestBody LoanCreationRequestDTO loan) {
        loanService.createLoan(loan);

        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setMessage(Constants.SUCCESSFUL_MESSAGE);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/{loanId:[0-9]+}/payments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<APIResponseDTO<String>> doPaymentToLoan(
            @PathVariable("loanId") int loanId, @Valid @RequestBody LoanPaymentRequestDTO loan) {
        loanService.payForLoanDebt(loanId, loan);

        APIResponseDTO<String> apiResponseDTO = new APIResponseDTO<>();

        apiResponseDTO.setMessage(Constants.PAYMENT_DONE_SUCCESSFULLY_MESSAGE);

        return new ResponseEntity<>(apiResponseDTO, HttpStatus.OK);
    }
}
