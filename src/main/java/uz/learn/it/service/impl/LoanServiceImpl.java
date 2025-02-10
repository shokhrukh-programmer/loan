package uz.learn.it.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanCreationRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.dto.response.DailyLoanPaymentDebtResponseDTO;
import uz.learn.it.dto.response.LoanPaymentHistoryResponseDTO;
import uz.learn.it.dto.response.LoanResponseDTO;
import uz.learn.it.entity.*;
import uz.learn.it.enums.PaymentTypeForLoan;
import uz.learn.it.enums.PaymentTypeForTransaction;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.exception.notfound.AccountNotFoundException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.exception.notfound.LoanNotFoundException;
import uz.learn.it.repository.*;
import uz.learn.it.service.LoanService;
import uz.learn.it.service.TransactionService;
import uz.learn.it.specification.LoanDebtSpecification;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    private final LoanDAO loanDAO;

    private final TransactionService transactionService;

    private final AccountDAO accountDAO;

    private final DailyLoanDebtDAO dailyLoanDebtDAO;

    private final ClientDAO clientDAO;

    private final LoanPaymentHistoryDAO loanPaymentHistoryDAO;

    private final JwtService jwtService;

    @Autowired
    public LoanServiceImpl(TransactionService transactionService, LoanDAO loanDAO,
                           AccountDAO accountDAO, DailyLoanDebtDAO dailyLoanDebtDAO,
                           ClientDAO clientDAO, LoanPaymentHistoryDAO loanPaymentHistoryDAO,
                           JwtService jwtService) {
        this.transactionService = transactionService;

        this.loanDAO = loanDAO;

        this.accountDAO = accountDAO;

        this.dailyLoanDebtDAO = dailyLoanDebtDAO;

        this.clientDAO = clientDAO;

        this.loanPaymentHistoryDAO = loanPaymentHistoryDAO;

        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void createLoan(LoanCreationRequestDTO loanRequest) {
        Client client = checkClientExistence(loanRequest);

        Loan loan = Loan.builder()
                .createdDate(LocalDate.now())
                .amount(loanRequest.getLoanAmount())
                .term(loanRequest.getLoanTerm())
                .interestRate(loanRequest.getInterestRate())
                .balance(loanRequest.getLoanAmount())
                .client(client)
                .build();

        loanDAO.save(loan);
    }

    @Override
    public List<LoanResponseDTO> getLoans() {
        List<Loan> loans = loanDAO.findAll();

        return loans.stream()
                .map(l -> new LoanResponseDTO(l.getId(), l.getCreatedDate(), l.getAmount(), l.getTerm(),
                        l.getInterestRate(), l.getBalance(), l.getDebt(), l.getClient().getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void calculateInterest() {
        List<Loan> loanList = loanDAO.findAll();

        double dailyInterest;

        for (Loan l : loanList) {
            dailyInterest = l.getBalance() / 100.0 * l.getInterestRate() / 365;

            l.setDebt(l.getDebt() + dailyInterest);

            loanDAO.save(l);

            DailyLoanPaymentDebt dailyLoanPaymentDebt = DailyLoanPaymentDebt.builder()
                    .date(LocalDate.now())
                    .dailyInterestAmount(dailyInterest)
                    .loan(l)
                    .build();

            dailyLoanDebtDAO.save(dailyLoanPaymentDebt);
        }
    }

    @Override
    public List<DailyLoanPaymentDebtResponseDTO> getDailyPaymentsById(long loanId, int page, int size,
                                             LocalDate fromDate, LocalDate toDate, HttpServletRequest request) throws AccessDeniedException {
        String token = jwtService.getTokenFromRequest(request);

        long id = jwtService.extractClientId(token);

        Specification<DailyLoanPaymentDebt> spec = LoanDebtSpecification.byClientId(id);

        if(loanDAO.getLoanById(loanId).orElseThrow(LoanNotFoundException::new).getClient().getId() != id &&
                !jwtService.extractRoles(token).equals("ROLE_MANAGER")) {
            throw new AccessDeniedException("You don't have correct rights to access this resource!");
        }

        List<DailyLoanPaymentDebt> debts = dailyLoanDebtDAO.getByLoanId(loanId);

        return debts.stream()
                .map(d -> new DailyLoanPaymentDebtResponseDTO(d.getId(), d.getDate(),
                        d.getDailyInterestAmount(), d.getLoan().getId()))
                .collect(Collectors.toList());
    }

    private Client checkClientExistence(LoanCreationRequestDTO loanRequest) {
        return clientDAO.getClientById(loanRequest.getClientId()).orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public void payForLoanDebt(long loanId, LoanPaymentRequestDTO loanDetails) {
        Loan loan = loanDAO.getLoanById(loanId).orElseThrow(LoanNotFoundException::new);

        Account account = accountDAO.getAccountsByAccountNumber(loanDetails.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(ExceptionMessageConstants.ACCOUNT_NOT_EXIST_BY_ACCOUNT_NUMBER));

        if (account.getClient().getId() != loan.getClient().getId()) {
            throw new ValidationException(ExceptionMessageConstants.INVALID_ACCOUNT_NUMBER);
        }

        if (loanDetails.getPaymentAmount() > account.getBalance()) {
            throw new ValidationException(ExceptionMessageConstants.BALANCE_NOT_VALID_MESSAGE);
        }

        if(loanDetails.getPaymentAmount() > loan.getBalance() + loan.getDebt()) {
            throw new ValidationException(ExceptionMessageConstants.INVALID_PAYMENT_AMOUNT);
        }

        doTransactionFromBalance(loanDetails, account);

        payForLoan(loanDetails, loan);
    }

    @Override
    public List<LoanPaymentHistoryResponseDTO> getLoanPaymentHistory(int page, int size, LocalDate fromDate, LocalDate toDate) {
        List<LoanPaymentHistory> paymentHistories = loanPaymentHistoryDAO.findAll();

        return paymentHistories.stream()
                .map(p -> new LoanPaymentHistoryResponseDTO(p.getId(), p.getType(), p.getAmount(),
                        p.getDate(), p.getLoan().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanPaymentHistoryResponseDTO> getLoanPaymentHistoryByLoanId(long loanId, HttpServletRequest request) throws AccessDeniedException {
        String token = jwtService.getTokenFromRequest(request);
        long id = jwtService.extractClientId(token);

        if(loanDAO.getLoanById(id).orElseThrow(LoanNotFoundException::new).getClient().getId() != id &&
                        !jwtService.extractRoles(token).equals("ROLE_MANAGER")) {
            throw new AccessDeniedException("You dont have access rights!");
        }

        List<LoanPaymentHistory> paymentHistories = loanPaymentHistoryDAO.getByLoanId(loanId);

        return paymentHistories.stream()
                .map(p -> new LoanPaymentHistoryResponseDTO(p.getId(), p.getType(), p.getAmount(),
                        p.getDate(), p.getLoan().getId()))
                .collect(Collectors.toList());
    }

    private void payForLoan(LoanPaymentRequestDTO loanDetails, Loan loan) {
        LoanPaymentHistory loanPaymentHistoryInterest = null;
        LoanPaymentHistory loanPaymentHistoryMain = null;

        if (loanDetails.getPaymentType().equals(PaymentTypeForLoan.INTEREST.name())) {
            double debt = loan.getDebt();

            if (debt >= loanDetails.getPaymentAmount()) {
                loan.setDebt(debt - loanDetails.getPaymentAmount());
                loanPaymentHistoryInterest = LoanPaymentHistory.builder()
                        .type(PaymentTypeForLoan.INTEREST)
                        .amount(loanDetails.getPaymentAmount())
                        .date(LocalDate.now())
                        .loan(loan)
                        .build();

            } else {
                loan.setDebt(0.0);
                loan.setBalance(loan.getBalance() - (loanDetails.getPaymentAmount() - debt));
                loanPaymentHistoryInterest = LoanPaymentHistory.builder()
                        .type(PaymentTypeForLoan.INTEREST)
                        .amount(debt)
                        .date(LocalDate.now())
                        .loan(loan)
                        .build();

                loanPaymentHistoryMain = LoanPaymentHistory.builder()
                        .type(PaymentTypeForLoan.MAIN)
                        .amount(loanDetails.getPaymentAmount() - debt)
                        .date(LocalDate.now())
                        .loan(loan)
                        .build();
            }
        } else {
            loan.setBalance(loan.getBalance() - loanDetails.getPaymentAmount());

            loanPaymentHistoryMain = LoanPaymentHistory.builder()
                    .type(PaymentTypeForLoan.MAIN)
                    .amount(loanDetails.getPaymentAmount())
                    .date(LocalDate.now())
                    .loan(loan)
                    .build();
        }

        loanDAO.save(loan);

        if(loanPaymentHistoryInterest != null) {
            loanPaymentHistoryDAO.save(loanPaymentHistoryInterest);
        }

        if(loanPaymentHistoryMain != null) {
            loanPaymentHistoryDAO.save(loanPaymentHistoryMain);
        }
    }

    private void doTransactionFromBalance(LoanPaymentRequestDTO loanDetails, Account account) {
        AccountTransactionRequestDTO accountTransactionRequestDTO = new AccountTransactionRequestDTO();

        accountTransactionRequestDTO.setType(PaymentTypeForTransaction.WITHDRAW.name());

        accountTransactionRequestDTO.setAmountToTopUpAndWithdraw(loanDetails.getPaymentAmount());

        transactionService.makeTransaction(account.getId(), accountTransactionRequestDTO);
    }
}