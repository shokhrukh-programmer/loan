package uz.learn.it.repository;

import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;

import java.util.List;
import java.util.Optional;

public interface LoanDAO {
    void saveLoan(Loan loan);

    List<Loan> getLoans();

    Optional<Loan> getLoanByLoanId(long loanId);

    void update(Loan loan);

    List<LoanPaymentHistory> getLoanPaymentHistory();

    List<LoanPaymentHistory> getLoanPaymentHistoryByLoanId(long loanId);

    void saveLoanPaymentHistory(LoanPaymentHistory loanPaymentHistory);
}
