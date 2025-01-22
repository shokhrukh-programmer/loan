package uz.learn.it.service;

import uz.learn.it.entity.Loan;
import uz.learn.it.dto.request.LoanCreationRequestDTO;

import java.util.List;

public interface LoanService {
    List<Loan> getLoans();

    void createLoan(LoanCreationRequestDTO loan);
}