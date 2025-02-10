package uz.learn.it.service;

import jakarta.servlet.http.HttpServletRequest;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.response.AccountResponseDTO;

import java.util.List;

public interface AccountService {
    void createAccount(AccountCreationRequestDTO accountCreationRequestDTO);

    List<AccountResponseDTO> getAccountsByClientId(long id, HttpServletRequest request);

    List<AccountResponseDTO> getAccountsByClientId(long id);

    List<AccountResponseDTO> getAccounts();
}
