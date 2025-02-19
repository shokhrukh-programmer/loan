import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.dto.response.AccountResponseDTO;
import uz.learn.it.entity.Account;
import uz.learn.it.entity.Client;
import uz.learn.it.enums.Role;
import uz.learn.it.helper.AccountNumberGenerator;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.service.impl.AccountServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AccountDAO.class)
public class AccountServiceTests {
    @Mock
    private AccountDAO accountDAO;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    private Client client;
    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initializes mocks
        client = Client.builder()
                .id(1)
                .firstName("Shohruh")
                .lastName("Alimov")
                .passportInfo("AC1525032")
                .phoneNumber("+998908991199")
                .role(Role.ROLE_MANAGER).build();

        account = Account.builder()
                .id(1)
                .accountNumber(AccountNumberGenerator.generateAccountNumber())
                .accountType("DEPOSIT")
                .balance(5000)
                .client(client).build();
    }

    @Test
    public void testFindAccounts() {
        Page<Account> pageAccount = new PageImpl<>(List.of(account));

        // Mock repository with Specification and PageRequest
        when(accountDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(pageAccount);

        List<AccountResponseDTO> result = accountServiceImpl.getAccounts(0, 10);

        assertEquals(1, result.size());
        assertEquals("DEPOSIT", result.get(0).getAccountType());
        //assertEquals(client.getId(), result.get(0).getClientId());
    }
}
