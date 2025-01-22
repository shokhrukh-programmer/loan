package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.*;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.exception.notfound.LoanNotFoundException;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.LoanService;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final TransactionService transactionService;

    @Autowired
    public LoanServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
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

        Storage.addLoan(loan);
    }

    @Override
    public List<Loan> getLoans() {
        return Storage.loans;
    }

    @Override
    public void calculateAndWriteInterest() {
        List<Loan> loanList = Storage.loans;

        double dailyInterest;

        for(Loan l : loanList) {
            dailyInterest = l.getBalance() / 100.0 * l.getInterestRate() / 365;

            l.setDebt(l.getDebt() + dailyInterest);

            DailyLoanPaymentDebt dailyLoanPaymentDebt = DailyLoanPaymentDebt.builder()
                    .date(DateFormatter.dateFormatter(new Date()))
                    .dailyInterestAmount(dailyInterest)
                    .loanId(l.getId())
                    .build();

            Storage.addToPaymentTable(dailyLoanPaymentDebt);
        }
    }

    @Override
    public List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId) {
        return Storage.getDailyPaymentsById(loanId);
    }

    private void checkClientExistence(LoanCreationRequestDTO loanRequest) {
        Storage.findClientById(loanRequest.getClientId())
                .orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public void payForLoanDebt(long loanId, LoanPaymentRequestDTO loanDetails) {
        Loan loan = getLoanById(loanId);

        Account account = getAccountByAccountNumber(loanDetails.getAccountNumber());

        if(loanDetails.getPaymentAmount() > account.getBalance()) {
            throw new ValidationException(Constants.BALANCE_NOT_VALID_MESSAGE);
        }

        doTransactionFromBalance(loanDetails, account);

        payForLoan(loanDetails, loan);
    }

    private void payForLoan(LoanPaymentRequestDTO loanDetails, Loan loan) {
        if(loanDetails.getPaymentType().equals(PaymentType.INTEREST.name())) {
            double debt = loan.getDebt();

            if(debt > loanDetails.getPaymentAmount()) {
                loan.setDebt(debt - loanDetails.getPaymentAmount());
            } else {
                loan.setDebt(0.0);
                loan.setBalance(loan.getBalance() - (loanDetails.getPaymentAmount() - debt));
            }
        } else {
            loan.setBalance(loan.getBalance() - loanDetails.getPaymentAmount());
        }
    }

    private Loan getLoanById(long loanId) {
        return Storage.findLoanById(loanId)
                .orElseThrow(LoanNotFoundException::new);
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        return Storage.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new ValidationException(Constants.ACCOUNT_NOT_EXIST_BY_ACCOUNT_NUMBER));
    }

    private void doTransactionFromBalance(LoanPaymentRequestDTO loanDetails, Account account) {
        AccountTransactionRequestDTO accountTransactionRequestDTO = new AccountTransactionRequestDTO();

        accountTransactionRequestDTO.setType(PaymentType.WITHDRAW.name());

        accountTransactionRequestDTO.setAmountToTopUpAndWithdraw(loanDetails.getPaymentAmount());

        transactionService.makeTransaction(account.getId(), accountTransactionRequestDTO);
    }
}
