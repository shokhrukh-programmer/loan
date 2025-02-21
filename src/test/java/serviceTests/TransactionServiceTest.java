package serviceTests;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import uz.learn.it.dto.response.TransactionHistoryResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.TransactionHistory;
import uz.learn.it.enums.Role;
import uz.learn.it.helper.AccountNumberGenerator;
import uz.learn.it.repository.TransactionDAO;
import uz.learn.it.service.impl.JwtService;
import uz.learn.it.service.impl.TransactionServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionDAO transactionDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Client client;

    private TransactionHistory transactionHistory;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .firstName("Shohruh")
                .lastName("Alimov")
                .passportInfo("AC1525032")
                .phoneNumber("+998908991199")
                .role(Role.ROLE_MANAGER).build();

        transactionHistory = TransactionHistory.builder()
                .id(1)
                .accountNumber(AccountNumberGenerator.generateAccountNumber())
                .operation("TOP_UP")
                .remainingBalance(213.89)
                .date(LocalDate.now())
                .client(client).build();
    }

    @Test
    public void shouldReturnOperationHistory() {
        Page<TransactionHistory> page = new PageImpl<>(List.of(transactionHistory));

        when(transactionDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(page);

        List<TransactionHistoryResponseDTO> result = transactionService
                .getOperationHistory(0, 10, LocalDate.now(), LocalDate.now());

        assertEquals("TOP_UP", result.get(0).getOperation());
        assertEquals(213.89, result.get(0).getRemainingBalance());
    }

    @Test
    public void shouldReturnOperationHistoryByClientId() {
        Page<TransactionHistory> page = new PageImpl<>(List.of(transactionHistory));

        when(transactionDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(page);

        List<TransactionHistoryResponseDTO> result = transactionService
                .getOperationHistoryByClientId(request, 0, 10, LocalDate.now(), LocalDate.MIN);

        assertEquals("TOP_UP", result.get(0).getOperation());
        assertEquals(213.89, result.get(0).getRemainingBalance());
    }
}
