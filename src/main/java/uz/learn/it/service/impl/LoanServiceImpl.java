package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.entity.Client;
import uz.learn.it.dto.PaymentType;
import uz.learn.it.entity.Account;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.exception.notfound.LoanNotFoundException;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.*;
import uz.learn.it.service.LoanService;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    private final TransactionService transactionService;
    private final LoanDAO loanDAO;
    private final DailyLoanPaymentDAO dailyLoanPaymentDAO;
    private final ClientDAO clientDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public LoanServiceImpl(LoanDAO loanDAO, TransactionService transactionService,
                           DailyLoanPaymentDAO dailyLoanPaymentDAO, ClientDAO clientDAO,
                           AccountDAO accountDAO) {
        this.transactionService = transactionService;
        this.loanDAO = loanDAO;
        this.dailyLoanPaymentDAO = dailyLoanPaymentDAO;
        this.clientDAO = clientDAO;
        this.accountDAO = accountDAO;
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

            dailyLoanPaymentDAO.saveDailyPayment(dailyLoanPaymentDebt);
        }
    }

    @Override
    public List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId) {
        return dailyLoanPaymentDAO.getDailyPaymentsByLoanId(loanId);
    }

    private void checkClientExistence(LoanCreationRequestDTO loanRequest) {
        Client client = clientDAO.findClientByClientId(loanRequest.getClientId());

        if(client == null) {
            throw new ClientNotFoundException();
        }
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
        Loan loan = loanDAO.findLoanByLoanId(loanId);

        if(loan == null) {
            throw new LoanNotFoundException();
        }

        return loan;
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        Account account = accountDAO.findAccountByAccountNumber(accountNumber);

        if(account == null) {
            throw new ValidationException(Constants.ACCOUNT_NOT_EXIST_BY_ACCOUNT_NUMBER);
        }

        return account;
    }

    private void doTransactionFromBalance(LoanPaymentRequestDTO loanDetails, Account account) {
        AccountTransactionRequestDTO accountTransactionRequestDTO = new AccountTransactionRequestDTO();

        accountTransactionRequestDTO.setType(PaymentType.WITHDRAW.name());

        accountTransactionRequestDTO.setAmountToTopUpAndWithdraw(loanDetails.getPaymentAmount());

        transactionService.makeTransaction(account.getId(), accountTransactionRequestDTO);
    }
}
