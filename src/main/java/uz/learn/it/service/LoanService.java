package uz.learn.it.service;

import uz.learn.it.dto.DailyLoanPaymentDebt;
import uz.learn.it.dto.Loan;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;

import java.util.List;

public interface LoanService {
    List<Loan> getLoans();
    String createLoan(LoanCreationRequestDTO loan);
    String payForLoan(LoanPaymentRequestDTO loanDetails);
    void calculateAndWriteInterest();
    List<DailyLoanPaymentDebt> getDailyPayments(int loanId);
}