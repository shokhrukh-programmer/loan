package uz.learn.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.entity.Account;
import uz.learn.it.entity.Client;
import uz.learn.it.enums.AccountType;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.AccountNumberGenerator;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.service.AccountService;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;

    private final ClientDAO clientDAO;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO, ClientDAO clientDAO) {
        this.accountDAO = accountDAO;
        this.clientDAO = clientDAO;
    }

    @Override
    public void createAccount(AccountCreationRequestDTO accountCreationRequestDTO) {
        String accountType = accountCreationRequestDTO.getAccountType();

        long clientId = accountCreationRequestDTO.getClientId();

        Client client = clientDAO.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        checkForAccountAlreadyExistence(clientId, accountType);

        Account account = Account.builder()
                .accountType(accountType)
                .accountNumber(
                        accountType.equals(AccountType.ACCOUNT.name()) ?
                                AccountNumberGenerator.generateAccountNumber() :
                                AccountNumberGenerator.generateDepositNumber()
                )
                .client(client)
                .build();

        accountDAO.saveAccount(account);
    }

    @Override
    public List<Account> getAccountsByClientId(long clientId) {
        return accountDAO.getAccountsByClientId(clientId);
    }

    @Override
    public List<Account> getAccounts() {
        return accountDAO.getAccounts();
    }

    private void checkForAccountAlreadyExistence(long clientId, String accountType) {
        List<Account> accounts = getAccountsByClientId(clientId);
        if (accounts != null) {
            for(Account a : accounts) {
                if(a.getAccountType().equals(accountType)) {
                    throw new AlreadyExistException(String.format(ExceptionMessageConstants.ACCOUNT_EXIST_MESSAGE,
                            accountType, clientId));
                }
            }
        }
    }
}
