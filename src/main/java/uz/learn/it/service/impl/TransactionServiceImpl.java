package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.entity.Account;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.enums.PaymentTypeForTransaction;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.exception.notfound.AccountNotFoundException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.repository.TransactionDAO;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionDAO transactionDAO;

    private final AccountDAO accountDAO;

    private final ClientDAO clientDAO;

    @Autowired
    public TransactionServiceImpl(TransactionDAO transactionDAO, AccountDAO accountDAO, ClientDAO clientDAO) {
        this.transactionDAO = transactionDAO;

        this.accountDAO = accountDAO;

        this.clientDAO = clientDAO;
    }

    @Override
    public List<TransactionHistory> getOperationHistory() {
        return transactionDAO.getTransactionHistory();
    }

    @Override
    public void makeTransaction(long id, AccountTransactionRequestDTO accountTransactionRequestDTO) {
        Account account = getAccountByAccountId(id);

        StringBuilder operation = getOperationByType(accountTransactionRequestDTO, account);

        Client client = clientDAO.getClientById(account.getClient().getId()).orElseThrow(ClientNotFoundException::new);

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .date(DateFormatter.dateFormatter(new Date()))
                .accountNumber(account.getAccountNumber())
                .operation(operation.append(accountTransactionRequestDTO.getAmountToTopUpAndWithdraw()).toString())
                .remainingBalance(account.getBalance())
                .client(client)
                .build();

        transactionDAO.saveTransaction(transactionHistory);
    }

    @Override
    public Account getAccountByAccountId(long accountId) {
        return accountDAO.getAccountByAccountId(accountId)
                .orElseThrow(AccountNotFoundException::new);
    }

    private StringBuilder getOperationByType(AccountTransactionRequestDTO accountTransactionRequestDTO, Account account) {
        StringBuilder operation = new StringBuilder();

        if(accountTransactionRequestDTO.getType().equals(PaymentTypeForTransaction.TOP_UP.name())) {
            account.setBalance(account.getBalance() + accountTransactionRequestDTO.getAmountToTopUpAndWithdraw());
            operation.append("+ ");
        } else {
            checkBalanceToWithdraw(accountTransactionRequestDTO, account);
            account.setBalance(account.getBalance() - accountTransactionRequestDTO.getAmountToTopUpAndWithdraw());
            operation.append("- ");
        }

        accountDAO.updateAccount(account);

        return operation;
    }


    private void checkBalanceToWithdraw(AccountTransactionRequestDTO accountTransactionRequestDTO, Account account) {
        if(account.getBalance() - accountTransactionRequestDTO.getAmountToTopUpAndWithdraw() < 0) {
            throw new ValidationException(ExceptionMessageConstants.BALANCE_NOT_VALID_MESSAGE);
        }
    }
}
