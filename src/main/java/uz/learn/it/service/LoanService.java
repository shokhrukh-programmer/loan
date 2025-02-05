package uz.learn.it.service;

import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;

import java.time.LocalDate;
import java.util.List;

public interface LoanService {
    List<Loan> getLoans();

    void createLoan(LoanCreationRequestDTO loan);

    void calculateInterest();

    List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId, int page, int size,
                                                    LocalDate fromDate, LocalDate toDate);

    void payForLoanDebt(long loanId, LoanPaymentRequestDTO loanDetails);

    List<LoanPaymentHistory> getLoanPaymentHistory(int page, int size, LocalDate fromDate, LocalDate toDate);

    List<LoanPaymentHistory> getLoanPaymentHistoryByLoanId(long loanId);
}