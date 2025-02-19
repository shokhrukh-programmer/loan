package uz.learn.it.service;

import jakarta.servlet.http.HttpServletRequest;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.dto.response.DailyLoanPaymentDebtResponseDTO;
import uz.learn.it.dto.response.LoanPaymentHistoryResponseDTO;
import uz.learn.it.dto.response.LoanResponseDTO;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;

public interface LoanService {
    List<LoanResponseDTO> getLoans(int page, int size);

    void createLoan(LoanCreationRequestDTO loan);

    void calculateInterest();

    List<DailyLoanPaymentDebtResponseDTO> getDailyPaymentsById(int page, int size,
                                                               LocalDate fromDate, LocalDate toDate, HttpServletRequest request) throws AccessDeniedException;

    void payForLoanDebt(long loanId, LoanPaymentRequestDTO loanDetails);

    List<LoanPaymentHistoryResponseDTO> getLoanPaymentHistory(int page, int size, LocalDate fromDate, LocalDate toDate);

    List<LoanPaymentHistoryResponseDTO> getLoanPaymentHistoryByClientId(HttpServletRequest request, int page, int size) throws AccessDeniedException;

    List<LoanResponseDTO> getLoansByClientId(HttpServletRequest request, int page, int size);
}