package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.Loan;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.*;
import uz.learn.it.service.LoanService;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanDAO loanDAO;

    private final ClientDAO clientDAO;

    @Autowired
    public LoanServiceImpl(LoanDAO loanDAO, ClientDAO clientDAO) {
        this.loanDAO = loanDAO;

        this.clientDAO = clientDAO;
    }

    @Override
    public void createLoan(LoanCreationRequestDTO loanRequest) {
        checkClientExistence(loanRequest);

        Loan loan = Loan.builder()
                .createdDate(DateFormatter.dateFormatter(new Date()))
                .amount(loanRequest.getLoanAmount())
                .term(loanRequest.getLoanTerm())
                .interestRate(loanRequest.getInterestRate())
                .balance(loanRequest.getLoanAmount())
                .clientId(loanRequest.getClientId())
                .build();

        loanDAO.saveLoan(loan);
    }

    @Override
    public List<Loan> getLoans() {
        return loanDAO.getLoans();
    }

    private void checkClientExistence(LoanCreationRequestDTO loanRequest) {
        Client client = clientDAO.findClientByClientId(loanRequest.getClientId());

        if(client == null) {
            throw new ClientNotFoundException();
        }
    }

}
