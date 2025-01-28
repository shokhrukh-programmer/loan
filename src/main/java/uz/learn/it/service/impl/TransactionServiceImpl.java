package uz.learn.it.service.impl;

import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.Account;
import uz.learn.it.dto.PaymentType;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.exception.notfound.AccountNotFoundException;
import uz.learn.it.exception.ValidationException;
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.TransactionService;

import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Override
    public List<TransactionHistory> getOperationHistory() {
        return Storage.getOperationHistories();
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

        Storage.addOperationToHistory(transactionHistory);
    }

    @Override
    public Account getAccountByAccountId(long accountId) {
        return Storage.findAccountById(accountId)
                .orElseThrow(AccountNotFoundException::new);
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
