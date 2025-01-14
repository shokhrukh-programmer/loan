package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.dto.Account;
import uz.learn.it.dto.Client;
import uz.learn.it.dto.TransactionHistory;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.request.AccountTransactionRequestDTO;
import uz.learn.it.dto.response.AccountCreationResponseDTO;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.exception.BalanceNotValidException;
import uz.learn.it.exception.NotFoundException;
import uz.learn.it.helper.AccountNumberGenerator;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.AccountService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private static int accountId = 1;
    private static int historyId = 1;
    private final Storage storage;

    @Autowired
    public AccountServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public AccountCreationResponseDTO createAccount(AccountCreationRequestDTO accountCreationRequestDTO) {
        String accountType = accountCreationRequestDTO.getAccountType();
        int id = accountCreationRequestDTO.getClientId();
        Client client = getClientById(id);

        checkForAccountAlreadyExistence(id, accountType);

        Account account = new Account(accountId++, accountType, accountType.equals("ACCOUNT") ?
                AccountNumberGenerator.generateAccountNumber() : AccountNumberGenerator.generateDepositNumber(), 0.0, accountCreationRequestDTO.getClientId());

        storage.addAccount(account);

        return new AccountCreationResponseDTO(
                client.getFirstName(), client.getLastName(), accountType, account.getAccountNumber()
        );
    }

    @Override
    public List<Account> getAccountsByClientId(int id) {
        List<Account> accounts = storage.getAccounts();

        return accounts.stream()
                .filter(account -> account.getClientId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public String doTransaction(int id, AccountTransactionRequestDTO accountTransactionRequestDTO) {
        Account account = getAccountById(id);

        StringBuilder operation = getOperationByType(accountTransactionRequestDTO, account);

        TransactionHistory transactionHistory = new TransactionHistory(
                historyId++, new Date(), account.getAccountNumber(),
                operation.append(accountTransactionRequestDTO.getAmountToTopUpAndWithdraw()).toString(),
                account.getBalance(),
                account.getClientId());

        storage.addOperationToHistory(transactionHistory);

        return "Operation has successfully done!";
    }

    @Override
    public List<TransactionHistory> getOperationHistory() {
        return storage.getOperationHistories();
    }

    @Override
    public List<Account> getAccounts() {
        return storage.getAccounts();
    }

    @Override
    public Account getAccountById(int id) {
        return storage.getAccounts().stream().filter(accountId -> accountId.getId() == id)
                .findFirst().orElseThrow(() -> new NotFoundException("There is no account with this id!"));
    }

    private Client getClientById(int id) {
        return storage.getClients().stream().filter(client -> client.getId() == id).findFirst()
                .orElseThrow(() -> new NotFoundException("There is no client with this id!"));
    }

    private void checkForAccountAlreadyExistence(int id, String accountType) {
        List<Account> accounts = getAccountsByClientId(id);

        for(Account a : accounts) {
            if(a.getAccountType().equals(accountType)) {
                throw new AlreadyExistException("This client has already opened "
                        + accountType);
            }
        }
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
