package uz.learn.it.repository;

import uz.learn.it.entity.Loan;

import java.util.List;
import java.util.Optional;

public interface LoanDAO {
    void saveLoan(Loan loan);

    List<Loan> getLoans();

    Optional<Loan> getLoanByLoanId(long loanId);

    void update(Loan loan);
}
