package uz.learn.it.service.impl;

import org.springframework.stereotype.Service;
import uz.learn.it.constant.Constants;
import uz.learn.it.dto.Account;
import uz.learn.it.dto.AccountType;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.helper.AccountNumberGenerator;
import uz.learn.it.repository.Storage;
import uz.learn.it.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public void createAccount(AccountCreationRequestDTO accountCreationRequestDTO) {
        String accountType = accountCreationRequestDTO.getAccountType();

        long clientId = accountCreationRequestDTO.getClientId();

        checkForAccountAlreadyExistence(clientId, accountType);

        Account account = Account.builder()
                .accountType(accountType)
                .accountNumber(
                        accountType.equals(AccountType.ACCOUNT.name()) ?
                                AccountNumberGenerator.generateAccountNumber() :
                                AccountNumberGenerator.generateDepositNumber()
                )
                .clientId(clientId)
                .build();

        Storage.addAccount(account);
    }

    @Override
    public List<Account> getAccountsByClientId(Long clientId) {
        return Storage.getAccountsByClientId(clientId);
    }

    @Override
    public List<Account> getAccounts() {
        return Storage.getAccounts();
    }

    private void checkForAccountAlreadyExistence(Long clientId, String accountType) {
        List<Account> accounts = getAccountsByClientId(clientId);

        for(Account a : accounts) {
            if(a.getAccountType().equals(accountType)) {
                throw new AlreadyExistException(String.format(Constants.ACCOUNT_EXIST_MESSAGE,
                        accountType, clientId));
            }
        }
    }
}
