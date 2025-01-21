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
import uz.learn.it.helper.DateFormatter;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.AccountService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private static int accountId = 1;
    private final Storage storage;

    @Autowired
    public AccountServiceImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public AccountCreationResponseDTO createAccount(AccountCreationRequestDTO accountCreationRequestDTO) {
        String accountType = accountCreationRequestDTO.getAccountType();
        Long clientId = accountCreationRequestDTO.getClientId();
        Client client = getClientById(clientId);

        checkForAccountAlreadyExistence(clientId, accountType);

        Account account = new Account(accountId++, accountType, accountType.equals("ACCOUNT") ?
                AccountNumberGenerator.generateAccountNumber() : AccountNumberGenerator.generateDepositNumber(), 0.0, accountCreationRequestDTO.getClientId());

        storage.addAccount(account);

        return new AccountCreationResponseDTO(
                client.getFirstName(), client.getLastName(), accountType, account.getAccountNumber()
        );
    }

    @Override
    public List<Account> getAccountsByClientId(Long clientId) {
        List<Account> accounts = storage.getAccounts();

        return accounts.stream()
                .filter(account -> account.getClientId() == clientId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> getAccounts() {
        return storage.getAccounts();
    }

    private Client getClientById(Long clientId) {
        return storage.getClients()
                .stream()
                .filter(client -> client.getId() == clientId).findFirst()
                .orElseThrow(() -> new NotFoundException("There is no client with this id!"));
    }

    private void checkForAccountAlreadyExistence(Long clientId, String accountType) {
        List<Account> accounts = getAccountsByClientId(clientId);

        for(Account a : accounts) {
            if(a.getAccountType().equals(accountType)) {
                throw new AlreadyExistException("This client has already opened "
                        + accountType);
            }
        }
    }
}
