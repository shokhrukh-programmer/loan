package controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uz.learn.it.LoanManagement;
import uz.learn.it.dto.request.ClientModificationRequestDTO;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.enums.Role;
import uz.learn.it.service.impl.ClientServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LoanManagement.class)
@AutoConfigureMockMvc
public class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ClientServiceImpl clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .firstName("Shohruh")
                .lastName("Alimov")
                .passportInfo("AC1525032")
                .phoneNumber("+998908991199")
                .role(Role.ROLE_MANAGER).build();
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    void testGetClients() throws Exception {
        when(clientService.getClients(0, 10)).thenReturn(List.of(client));

        mockMvc.perform(get("/api/clients?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].firstName").value("Shohruh"))
                .andExpect(jsonPath("$.data[0].lastName").value("Alimov"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    void testPutClientsById() throws Exception {
        ClientModificationRequestDTO requestDTO = new ClientModificationRequestDTO();

        requestDTO.setFirstName("Farruh");
        requestDTO.setPhoneNumber("+998906741673");

        mockMvc.perform(put("/api/clients/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"));
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    void testResetPasswordByClientsById() throws Exception {
        ClientRegistrationResponseDTO responseDTO =
                ClientRegistrationResponseDTO.builder()
                        .username("+998908991199")
                        .password("skaje12kas").build();

        when(clientService.resetPassword(any(Long.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/clients/reset-password/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value("+998908991199"))
                .andExpect(jsonPath("$.data.password").value("skaje12kas"));
    }
}
