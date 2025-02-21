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
import uz.learn.it.dto.response.DailyLoanPaymentDebtResponseDTO;
import uz.learn.it.dto.response.LoanPaymentHistoryResponseDTO;
import uz.learn.it.dto.response.LoanResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.DailyLoanPaymentDebt;
import uz.learn.it.entity.Loan;
import uz.learn.it.entity.LoanPaymentHistory;
import uz.learn.it.enums.PaymentTypeForLoan;
import uz.learn.it.enums.Role;
import uz.learn.it.repository.DailyLoanDebtDAO;
import uz.learn.it.repository.LoanDAO;
import uz.learn.it.repository.LoanPaymentHistoryDAO;
import uz.learn.it.service.impl.JwtService;
import uz.learn.it.service.impl.LoanServiceImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @Mock
    private LoanDAO loanDAO;

    @Mock
    private DailyLoanDebtDAO dailyLoanDebtDAO;

    @Mock
    private LoanPaymentHistoryDAO loanPaymentHistoryDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private LoanServiceImpl loanService;

    private Loan loan;

    private Client client;

    private DailyLoanPaymentDebt dailyLoanPaymentDebt;

    private LoanPaymentHistory loanPaymentHistory;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .firstName("Shohruh")
                .lastName("Alimov")
                .passportInfo("AC1525032")
                .phoneNumber("+998908991199")
                .role(Role.ROLE_MANAGER).build();

        loan = Loan.builder()
                .id(1)
                .createdDate(LocalDate.now())
                .amount(120000)
                .debt(0)
                .balance(10000)
                .term(60)
                .interestRate(25)
                .client(client).build();

        dailyLoanPaymentDebt = DailyLoanPaymentDebt.builder()
                .id(1)
                .dailyInterestAmount(155.89)
                .date(LocalDate.now())
                .loan(loan)
                .build();

        loanPaymentHistory = LoanPaymentHistory.builder()
                .id(1)
                .type(PaymentTypeForLoan.INTEREST)
                .amount(120000)
                .date(LocalDate.now())
                .loan(loan)
                .build();
    }

    @Test
    public void shouldReturnAllLoans() {
        Page<Loan> loanPage = new PageImpl<>(List.of(loan));

        when(loanDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(loanPage);

        List<LoanResponseDTO> result = loanService.getLoans(0, 10);

        assertEquals(loan.getBalance(), result.get(0).getBalance());
        assertEquals(loan.getClient().getId(), result.get(0).getClientId());
    }

    @Test
    public void shouldReturnDailyPaymentsById() {
        Page<DailyLoanPaymentDebt> dailyPaymentPage = new PageImpl<>(List.of(dailyLoanPaymentDebt));

        when(dailyLoanDebtDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(dailyPaymentPage);

        List<DailyLoanPaymentDebtResponseDTO> result = loanService
                .getDailyPaymentsById(0, 10, LocalDate.now(), LocalDate.now(), request);

        assertEquals(dailyLoanPaymentDebt.getDate(), result.get(0).getDate());
        assertEquals(dailyLoanPaymentDebt.getDailyInterestAmount(), result.get(0).getDailyInterestAmount());
    }

    @Test
    public void shouldReturnLoanPaymentHistory() {
        Page<LoanPaymentHistory> dailyPaymentPage = new PageImpl<>(List.of(loanPaymentHistory));

        when(loanPaymentHistoryDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(dailyPaymentPage);

        List<LoanPaymentHistoryResponseDTO> result = loanService
                .getLoanPaymentHistory(0, 10, LocalDate.now(), LocalDate.now());

        assertEquals(loanPaymentHistory.getDate(), result.get(0).getDate());
        assertEquals(loanPaymentHistory.getType(), result.get(0).getType());
    }
}
