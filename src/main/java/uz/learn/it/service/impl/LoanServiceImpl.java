package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.entity.*;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.enums.PaymentTypeForLoan;
import uz.learn.it.enums.PaymentTypeForTransaction;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.exception.notfound.AccountNotFoundException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.exception.notfound.LoanNotFoundException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.ClientDAO;
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

    private final ClientDAO clientDAO;

    @Autowired
    public LoanServiceImpl(TransactionService transactionService, LoanDAO loanDAO,
                           AccountDAO accountDAO, DailyLoanDebtDAO dailyLoanDebtDAO,
                           ClientDAO clientDAO) {
        this.transactionService = transactionService;

        this.loanDAO = loanDAO;

        this.accountDAO = accountDAO;

        this.dailyLoanDebtDAO = dailyLoanDebtDAO;

        this.clientDAO = clientDAO;
    }

    @Override
    public void createLoan(LoanCreationRequestDTO loanRequest) {
        Client client = checkClientExistence(loanRequest);

        Loan loan = Loan.builder()
                .createdDate(DateFormatter.dateFormatter(new Date()))
                .amount(loanRequest.getLoanAmount())
                .term(loanRequest.getLoanTerm())
                .interestRate(loanRequest.getInterestRate())
                .balance(loanRequest.getLoanAmount())
                .client(client)
                .build();

        loanDAO.saveLoan(loan);
    }

    @Override
    public List<Loan> getLoans() {
        return loanDAO.getLoans();
    }

    @Override
    public void calculateInterest() {
        List<Loan> loanList = loanDAO.getLoans();

        double dailyInterest;

        for(Loan l : loanList) {
            dailyInterest = l.getBalance() / 100.0 * l.getInterestRate() / 365;

            l.setDebt(l.getDebt() + dailyInterest);

            loanDAO.update(l);

            DailyLoanPaymentDebt dailyLoanPaymentDebt = DailyLoanPaymentDebt.builder()
                    .date(DateFormatter.dateFormatter(new Date()))
                    .dailyInterestAmount(dailyInterest)
                    .loan(l)
                    .build();

            dailyLoanDebtDAO.saveDailyLoanDebt(dailyLoanPaymentDebt);
        }
    }

    @Override
    public List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId) {
        return dailyLoanDebtDAO.getDailyLoanDebtsByLoanId(loanId);
    }

    private Client checkClientExistence(LoanCreationRequestDTO loanRequest) {
        return clientDAO.getClientById(loanRequest.getClientId()).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public void payForLoanDebt(long loanId, LoanPaymentRequestDTO loanDetails) {
        Loan loan = loanDAO.getLoanByLoanId(loanId).orElseThrow(LoanNotFoundException::new);

        Account account = accountDAO.getAccountByAccountNumber(loanDetails.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessageConstants.ACCOUNT_NOT_EXIST_BY_ACCOUNT_NUMBER));

        if(loanDetails.getPaymentAmount() > account.getBalance()) {
            throw new ValidationException(ExceptionMessageConstants.BALANCE_NOT_VALID_MESSAGE);
        }

        doTransactionFromBalance(loanDetails, account);

        payForLoan(loanDetails, loan);
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistory() {
        return loanDAO.getLoanPaymentHistory();
    }

    @Override
    public List<LoanPaymentHistory> getLoanPaymentHistoryByLoanId(long loanId) {
        return loanDAO.getLoanPaymentHistoryByLoanId(loanId);
    }

    private void payForLoan(LoanPaymentRequestDTO loanDetails, Loan loan) {
        LoanPaymentHistory loanPaymentHistory = null;
        if(loanDetails.getPaymentType().equals(PaymentTypeForLoan.INTEREST.name())) {
            double debt = loan.getDebt();

            if(debt > loanDetails.getPaymentAmount()) {
                loan.setDebt(debt - loanDetails.getPaymentAmount());
                loanPaymentHistory = LoanPaymentHistory.builder()
                        .amount(loanDetails.getPaymentAmount())
                        .interestPayment(loanDetails.getPaymentAmount())
                        .date(DateFormatter.dateFormatter(new Date()))
                        .loan(loan)
                        .build();
            } else {
                loan.setDebt(0.0);
                loan.setBalance(loan.getBalance() - (loanDetails.getPaymentAmount() - debt));
                loanPaymentHistory = LoanPaymentHistory.builder()
                        .amount(loanDetails.getPaymentAmount())
                        .interestPayment(debt)
                        .mainPayment(loanDetails.getPaymentAmount() - debt)
                        .date(DateFormatter.dateFormatter(new Date()))
                        .loan(loan)
                        .build();
            }
        } else {
            loan.setBalance(loan.getBalance() - loanDetails.getPaymentAmount());
        }

        loanDAO.update(loan);
        loanDAO.saveLoanPaymentHistory(loanPaymentHistory);
    }

    private void doTransactionFromBalance(LoanPaymentRequestDTO loanDetails, Account account) {
        AccountTransactionRequestDTO accountTransactionRequestDTO = new AccountTransactionRequestDTO();

        accountTransactionRequestDTO.setType(PaymentTypeForTransaction.WITHDRAW.name());

        accountTransactionRequestDTO.setAmountToTopUpAndWithdraw(loanDetails.getPaymentAmount());

        transactionService.makeTransaction(account.getId(), accountTransactionRequestDTO);
    }
}