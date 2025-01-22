package uz.learn.it.repository;

import uz.learn.it.entity.Loan;

import java.util.List;

public interface LoanDAO {
    List<Loan> getLoans();

    void saveLoan(Loan loan);

    Loan findLoanByLoanId(long loanId);
}
