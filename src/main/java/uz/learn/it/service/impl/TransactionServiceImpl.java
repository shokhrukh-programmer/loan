package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.Account;
import uz.learn.it.dto.Loan;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.request.LoanPaymentRequestDTO;
import uz.learn.it.exception.BalanceNotValidException;
import uz.learn.it.exception.NotFoundException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final Storage storage;
    private static int historyId = 1;

    @Autowired
    public TransactionServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public List<TransactionHistory> getOperationHistory() {
        return storage.getOperationHistories();
    }

    @Override
    public String doTransaction(int id, AccountTransactionRequestDTO accountTransactionRequestDTO) {
        Account account = getAccountById(id);

        StringBuilder operation = getOperationByType(accountTransactionRequestDTO, account);

        TransactionHistory transactionHistory = new TransactionHistory(
                historyId++, DateFormatter.dateFormatter(new Date()), account.getAccountNumber(),
                operation.append(accountTransactionRequestDTO.getAmountToTopUpAndWithdraw()).toString(),
                account.getBalance(),
                account.getClientId());

        storage.addOperationToHistory(transactionHistory);

        return "Operation has successfully done!";
    }

    @Override
    public Account getAccountById(int id) {
        return storage.getAccounts().stream().filter(accountId -> accountId.getId() == id)
                .findFirst().orElseThrow(() -> new NotFoundException("There is no account with this id!"));
    }

    private static StringBuilder getOperationByType(AccountTransactionRequestDTO accountTransactionRequestDTO, Account account) {
        StringBuilder operation = new StringBuilder();

        if(accountTransactionRequestDTO.getType().equals("top-up")) {
            account.setBalance(account.getBalance() + accountTransactionRequestDTO.getAmountToTopUpAndWithdraw());
            operation.append("+ ");
        } else {
            checkBalanceToWithdraw(accountTransactionRequestDTO, account);
            account.setBalance(account.getBalance() - accountTransactionRequestDTO.getAmountToTopUpAndWithdraw());
            operation.append("- ");
        }
        return operation;
    }


    private static void checkBalanceToWithdraw(AccountTransactionRequestDTO accountTransactionRequestDTO, Account account) {
        if(account.getBalance() - accountTransactionRequestDTO.getAmountToTopUpAndWithdraw() < 0) {
            throw new BalanceNotValidException("Balance is not enough to withdraw!");
        }
    }





}
