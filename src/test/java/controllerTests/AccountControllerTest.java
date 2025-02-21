package controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uz.learn.it.LoanManagement;
import uz.learn.it.dto.request.AccountCreationRequestDTO;
import uz.learn.it.dto.response.AccountResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.enums.Role;
import uz.learn.it.service.impl.AccountServiceImpl;
import uz.learn.it.service.impl.JwtService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LoanManagement.class)
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @MockitoBean
    private AccountServiceImpl accountService;

    private AccountResponseDTO account;

    private Client client;

    @BeforeEach
    public void setup() {
        client = Client.builder()
                .id(1L)
                .firstName("Shohruh")
                .lastName("Alimov")
                .passportInfo("AC1525032")
                .phoneNumber("+998908991199")
                .role(Role.ROLE_MANAGER).build();

        account = AccountResponseDTO.builder()
                .id(1)
                .accountType("DEPOSIT")
                .accountNumber("22543156456321")
                .balance(123123)
                .clientId(client.getId()).build();
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    void testGetAccounts() throws Exception {
        when(accountService.getAccounts(any(Integer.class), any(Integer.class))).thenReturn(List.of(account));

        mockMvc.perform(get("/api/accounts?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].accountType").value("DEPOSIT"))
                .andExpect(jsonPath("$.data[0].balance").value("123123.00"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    void testGetMyAccounts() throws Exception {
        when(accountService.getAccountsByClientId(any(HttpServletRequest.class))).thenReturn(List.of(account));

        mockMvc.perform(get("/api/accounts/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].accountType").value("DEPOSIT"))
                .andExpect(jsonPath("$.data[0].balance").value("123123.00"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    void testCreateAccount() throws Exception {
        AccountCreationRequestDTO requestDTO = AccountCreationRequestDTO.builder()
                .clientId(client.getId())
                .accountType("DEPOSIT").build();

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Account has successfully opened!"));
    }
}