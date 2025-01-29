package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.entity.Account;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;
import uz.learn.it.enums.PaymentTypeForLoan;
import uz.learn.it.enums.PaymentTypeForTransaction;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.exception.notfound.LoanNotFoundException;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.LoanService;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    private final TransactionService transactionService;

    @Autowired
    public LoanServiceImpl(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void createLoan(LoanCreationRequestDTO loanRequest) {
        validateClientExistence(loanRequest);

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
        return Storage.getLoans();
    }

    @Override
    public void calculateInterest() {
        List<Loan> loanList = Storage.getLoans();

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

    private void validateClientExistence(LoanCreationRequestDTO loanRequest) {
        Storage.findClientById(loanRequest.getClientId())
                .orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public void payForLoanDebt(long loanId, LoanPaymentRequestDTO loanDetails) {
        Loan loan = getLoanById(loanId);

        Account account = getAccountByAccountNumber(loanDetails.getAccountNumber());

        if(loanDetails.getPaymentAmount() > account.getBalance()) {
            throw new ValidationException(ExceptionMessageConstants.BALANCE_NOT_VALID_MESSAGE);
        }

        doTransactionFromBalance(loanDetails, account);

        payForLoan(loanDetails, loan);
    }

    private void payForLoan(LoanPaymentRequestDTO loanDetails, Loan loan) {
        if(loanDetails.getPaymentType().equals(PaymentTypeForLoan.INTEREST.name())) {
            double debt = loan.getDebt();

            if(debt > loanDetails.getPaymentAmount()) {
                loan.setDebt(debt - loanDetails.getPaymentAmount());
                Storage.addLoanPayment(
                        LoanPaymentHistory.builder()
                                .amount(loanDetails.getPaymentAmount())
                                .interestPayment(loanDetails.getPaymentAmount())
                                .mainPayment(0.0)
                                .date(DateFormatter.dateFormatter(new Date()))
                                .loanId(loan.getId())
                                .build());

            } else {
                Storage.addLoanPayment(
                        LoanPaymentHistory.builder()
                                .amount(loanDetails.getPaymentAmount())
                                .interestPayment(loan.getDebt())
                                .mainPayment(loanDetails.getPaymentAmount() - loan.getDebt())
                                .date(DateFormatter.dateFormatter(new Date()))
                                .loanId(loan.getId())
                                .build());
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
                .orElseThrow(() -> new ValidationException(ExceptionMessageConstants.ACCOUNT_NOT_EXIST_BY_ACCOUNT_NUMBER));
    }

    private void doTransactionFromBalance(LoanPaymentRequestDTO loanDetails, Account account) {
        AccountTransactionRequestDTO accountTransactionRequestDTO = new AccountTransactionRequestDTO();

        accountTransactionRequestDTO.setType(PaymentTypeForTransaction.WITHDRAW.name());

        accountTransactionRequestDTO.setAmountToTopUpAndWithdraw(loanDetails.getPaymentAmount());

        transactionService.makeTransaction(account.getId(), accountTransactionRequestDTO);
    }

    public List<LoanPaymentHistory> getLoanPaymentHistory() {
        return Storage.getLoanPaymentHistoryList();
    }

    public List<LoanPaymentHistory> getLoanPaymentHistoryByLoanId(long loanId) {
        return Storage.getLoanPaymentHistoryList().stream().filter(l -> l.getLoanId() == loanId)
                .collect(Collectors.toList());
    }
}
