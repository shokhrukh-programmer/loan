package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.PaymentType;
import uz.learn.it.entity.*;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.DailyLoanDebtDAO;
import uz.learn.it.repository.LoanDAO;
import uz.learn.it.service.LoanService;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanDAO loanDAO;
    private final TransactionService transactionService;
    private final AccountDAO accountDAO;
    private final DailyLoanDebtDAO dailyLoanDebtDAO;

    @Autowired
    public LoanServiceImpl(TransactionService transactionService, LoanDAO loanDAO,
                           AccountDAO accountDAO, DailyLoanDebtDAO dailyLoanDebtDAO) {
        this.transactionService = transactionService;
        this.loanDAO = loanDAO;
        this.accountDAO = accountDAO;
        this.dailyLoanDebtDAO = dailyLoanDebtDAO;
    }

    @Override
    public void createLoan(LoanCreationRequestDTO loanRequest) {
        //checkClientExistence(loanRequest);

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

    @Override
    public void calculateAndWriteInterest() {
        List<Loan> loanList = loanDAO.getLoans();

        double dailyInterest;

        for(Loan l : loanList) {
            dailyInterest = l.getBalance() / 100.0 * l.getInterestRate() / 365;

            l.setDebt(l.getDebt() + dailyInterest);

            DailyLoanPaymentDebt dailyLoanPaymentDebt = DailyLoanPaymentDebt.builder()
                    .date(DateFormatter.dateFormatter(new Date()))
                    .dailyInterestAmount(dailyInterest)
                    .loanId(l.getId())
                    .build();

            dailyLoanDebtDAO.saveDailyLoanDebt(dailyLoanPaymentDebt);
        }
    }

    @Override
    public List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId) {
        return dailyLoanDebtDAO.getDailyLoanDebtsByLoanId(loanId);
    }

//    private void checkClientExistence(LoanCreationRequestDTO loanRequest) {
//        Storage.findClientById(loanRequest.getClientId())
//                .orElseThrow(ClientNotFoundException::new);
//    }

    @Override
    public void payForLoanDebt(long loanId, LoanPaymentRequestDTO loanDetails) {
        Loan loan = loanDAO.getLoanByLoanId(loanId);

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
        return loanDAO.getLoanByLoanId(loanId);
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        return accountDAO.getAccountsByAccountNumber(accountNumber);
    }

    private void doTransactionFromBalance(LoanPaymentRequestDTO loanDetails, Account account) {
        AccountTransactionRequestDTO accountTransactionRequestDTO = new AccountTransactionRequestDTO();

        accountTransactionRequestDTO.setType(PaymentType.WITHDRAW.name());

        accountTransactionRequestDTO.setAmountToTopUpAndWithdraw(loanDetails.getPaymentAmount());

        transactionService.makeTransaction(account.getId(), accountTransactionRequestDTO);
    }
}
