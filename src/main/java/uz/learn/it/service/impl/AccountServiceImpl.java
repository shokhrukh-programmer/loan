package uz.learn.it.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.learn.it.constants.ExceptionMessageConstants;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.response.AccountResponseDTO;
import uz.learn.it.entity.Account;
import uz.learn.it.entity.Client;
import uz.learn.it.enums.AccountType;
import uz.learn.it.exception.AlreadyExistException;
import uz.learn.it.exception.notfound.AccountNotFoundException;
import uz.learn.it.exception.notfound.ClientNotFoundException;
import uz.learn.it.helper.AccountNumberGenerator;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.service.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;

    private final ClientDAO clientDAO;

    private final JwtService jwtService;

    @Autowired
    public AccountServiceImpl(AccountDAO accountDAO, ClientDAO clientDAO, JwtService jwtService) {
        this.accountDAO = accountDAO;
        this.clientDAO = clientDAO;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
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

        accountDAO.save(account);
    }

    @Override
    public List<AccountResponseDTO> getAccountsByClientId(long clientId, HttpServletRequest request) {
        String token = jwtService.getTokenFromRequest(request);
        long id = jwtService.extractClientId(token);

        if(id != clientId && !jwtService.extractRoles(token).equals("MANAGER")) {
            throw new AccountNotFoundException("You can see only your accounts!");
        }

        List<Account> accounts = accountDAO.findByClientId(clientId);

        return accounts.stream()
                .map(a -> new AccountResponseDTO(a.getId(), a.getAccountType(),
                        a.getAccountNumber(), a.getBalance(), a.getClient().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountResponseDTO> getAccountsByClientId(long id) {
        List<Account> accounts = accountDAO.findByClientId(id);

        return accounts.stream()
                .map(a -> new AccountResponseDTO(a.getId(), a.getAccountType(),
                        a.getAccountNumber(), a.getBalance(), a.getClient().getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountResponseDTO> getAccounts() {
        List<Account> accounts = accountDAO.findAll();

        return accounts.stream()
                .map(a -> new AccountResponseDTO(a.getId(), a.getAccountType(),
                        a.getAccountNumber(), a.getBalance(), a.getClient().getId()))
                .collect(Collectors.toList());
    }

    private void checkForAccountAlreadyExistence(long clientId, String accountType) {
        List<AccountResponseDTO> accounts = getAccountsByClientId(clientId);
        if (accounts != null) {
            for (AccountResponseDTO a : accounts) {
                if (a.getAccountType().equals(accountType)) {
                    throw new AlreadyExistException(String.format(ExceptionMessageConstants.ACCOUNT_EXIST_MESSAGE,
                            accountType, clientId));
                }
            }
        }
    }
}
