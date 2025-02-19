import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import uz.learn.it.dto.response.AccountResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.enums.Role;
import uz.learn.it.helper.AccountNumberGenerator;
import uz.learn.it.repository.AccountDAO;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.service.impl.AccountServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AccountDAO.class)
public class AccountServiceTests {
    @Mock
    private AccountDAO accountDAO;

    @Mock
    private ClientDAO clientDAO;

    @InjectMocks
    private AccountServiceImpl accountServiceImpl;

    private Client client;
    private AccountResponseDTO account;

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
    }

    @Test
    public void testFindAccounts() {
        account = AccountResponseDTO.builder()
                .id(1)
                .accountNumber(AccountNumberGenerator.generateAccountNumber())
                .accountType("DEPOSIT")
                .balance(5000)
                .clientId(1L).build();

        when(accountServiceImpl.getAccounts(0, 10)).thenReturn(List.of(account));

        List<AccountResponseDTO> result = accountServiceImpl.getAccounts(0, 10);

        assertEquals(1, result.size());

        assertEquals("DEPOSIT", result.get(0).getAccountType());
    }
}
