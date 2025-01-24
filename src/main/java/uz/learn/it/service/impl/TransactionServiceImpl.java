package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.entity.Account;
import uz.learn.it.dto.PaymentType;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.TransactionDAO;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDAO;
    private final AccountDAO accountDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO transactionDAO, AccountDAO accountDAO) {
        this.transactionDAO = transactionDAO;
        this.accountDAO = accountDAO;
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

        transactionDAO.saveTransaction(transactionHistory);
    }

    @Override
    public Account getAccountByAccountId(long accountId) {
        return accountDAO.getAccountById(accountId);
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
}
