package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.entity.Account;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;
import uz.learn.it.enums.PaymentType;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.exception.notfound.AccountNotFoundException;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.exception.notfound.LoanNotFoundException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.LoanDAO;
import uz.learn.it.repository.TransactionDAO;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDAO;

    private final AccountDAO accountDAO;

    private final LoanDAO loanDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO transactionDAO, AccountDAO accountDAO, LoanDAO loanDAO) {
        this.transactionDAO = transactionDAO;

        this.accountDAO = accountDAO;

        this.loanDAO = loanDAO;
    }

    @Override
    public List<TransactionHistory> getOperationHistory() {
        return transactionDAO.getTransactionHistory();
    }

    @Override
    public void makeTransaction(long id, AccountTransactionRequestDTO accountTransactionRequestDTO) {
        Account account = getAccountByAccountId(id);

        StringBuilder operation = getOperationByType(accountTransactionRequestDTO, account);

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .date(DateFormatter.dateFormatter(new Date()))
                .accountNumber(account.getAccountNumber())
                .operation(operation.append(accountTransactionRequestDTO.getAmountToTopUpAndWithdraw()).toString())
                .remainingBalance(account.getBalance())
                .clientId(account.getClientId())
                .build();

        transactionDAO.saveOperationToHistory(transactionHistory);
    }

    @Override
    public Account getAccountByAccountId(long accountId) {
        Account account = accountDAO.findAccountById(accountId);

        if(account == null) {
            throw new AccountNotFoundException();
        }

        return account;
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

            transactionDAO.saveDailyPayment(dailyLoanPaymentDebt);
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

    @Override
    public List<DailyLoanPaymentDebt> getDailyPaymentsById(long loanId) {
        return transactionDAO.getDailyDebtHistory(loanId);
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

    private StringBuilder getOperationByType(AccountTransactionRequestDTO accountTransactionRequestDTO, Account account) {
        StringBuilder operation = new StringBuilder();

        if(accountTransactionRequestDTO.getType().equals(PaymentType.TOP_UP.name())) {
            account.setBalance(account.getBalance() + accountTransactionRequestDTO.getAmountToTopUpAndWithdraw());
            operation.append("+ ");
        } else {
            checkBalanceToWithdraw(accountTransactionRequestDTO, account);
            account.setBalance(account.getBalance() - accountTransactionRequestDTO.getAmountToTopUpAndWithdraw());
            operation.append("- ");
        }
        return operation;
    }


    private void checkBalanceToWithdraw(AccountTransactionRequestDTO accountTransactionRequestDTO, Account account) {
        if(account.getBalance() - accountTransactionRequestDTO.getAmountToTopUpAndWithdraw() < 0) {
            throw new ValidationException(Constants.BALANCE_NOT_VALID_MESSAGE);
        }
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

        makeTransaction(account.getId(), accountTransactionRequestDTO);
    }
}
