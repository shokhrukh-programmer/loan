package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.*;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.exception.BalanceNotValidException;
import uz.learn.it.exception.NotFoundException;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.AccountService;
import uz.learn.it.service.LoanService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    private static int loanId = 1;
    private final Storage storage;
    private final AccountService accountService;
    private static int historyId = 1;

    @Autowired
    public LoanServiceImpl(Storage storage, AccountService accountService) {
        this.storage = storage;
        this.accountService = accountService;
    }

    @Override
    public String createLoan(LoanCreationRequestDTO loanRequest) {
        checkClientExistence(loanRequest);

        Loan loan = new Loan(loanId++, new Date(), loanRequest.getLoanAmount(), loanRequest.getLoanTenure(),
                loanRequest.getInterestRate(), loanRequest.getLoanAmount(), loanRequest.getClientId());

        storage.addLoan(loan);
        calculateAndWriteInterest();

        return "Loan was successfully given!";
    }

    @Override
    public List<Loan> getLoans() {
        return storage.getLoans();
    }

    @Override
    public String payForLoan(LoanPaymentRequestDTO loanDetails) {
        Loan loan = getLoanById(loanDetails);

        Account account = getAccountById(loanDetails);

        if(loanDetails.getPaymentAmount() > account.getBalance()) {
            throw new BalanceNotValidException("You have no enough money to pay loan!");
        }

        doTransactionFromBalance(loanDetails, account);

        if(loanDetails.getPaymentType().equals("INTEREST")) {
            double daily = loan.getBalance() / 100.0 * loan.getInterestRate() / 365;

            if(daily < loanDetails.getPaymentAmount()) {
                loan.setBalance(loan.getBalance() - (loanDetails.getPaymentAmount() - daily));
            }
        } else {
            loan.setBalance(loan.getBalance() - loanDetails.getPaymentAmount());
        }

        return "Payment was successfully done!";
    }

    @Override
    public void calculateAndWriteInterest() {
        List<Loan> loanList = storage.getLoans();
        DailyLoanPaymentTable dailyLoanPaymentTable;
        double dailyInterest = 0;

        for(Loan l : loanList) {
            dailyInterest = l.getBalance() / 100.0 * l.getInterestRate() / 365;
            dailyLoanPaymentTable = new DailyLoanPaymentTable(historyId++, new Date(), dailyInterest, l.getId());

            storage.addToPaymentTable(dailyLoanPaymentTable);
        }
    }

    @Override
    public List<DailyLoanPaymentTable> getDailyPayments(int loanId) {
        return storage.getLoanPaymentTableList().stream().filter(loan -> loan.getLoanId() == loanId).collect(Collectors.toList());
    }

    private void checkClientExistence(LoanCreationRequestDTO loanRequest) {
        storage.getClients().stream().filter(
                        c -> c.getId() == loanRequest.getClientId())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("There is no client with this id!"));
    }

    private void doTransactionFromBalance(LoanPaymentRequestDTO loanDetails, Account account) {
        AccountTransactionRequestDTO a = new AccountTransactionRequestDTO();
        a.setType("withdraw");
        a.setAmountToTopUpAndWithdraw(loanDetails.getPaymentAmount());
        accountService.doTransaction(account.getId(), a);
    }

    private Account getAccountById(LoanPaymentRequestDTO loanDetails) {
        return storage.getAccounts().stream().filter(a -> a.getAccountNumber()
                        .equals(loanDetails.getAccountNumber())).findFirst()
                .orElseThrow(() -> new NotFoundException("There is no account with this account number!"));
    }

    private Loan getLoanById(LoanPaymentRequestDTO loanDetails) {
        return storage.getLoans().stream().filter(l -> l.getId() == loanDetails.getLoanId())
                .findFirst().orElseThrow(() -> new NotFoundException("There is no loan with this loan id!"));
    }
}
