package controllerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import uz.learn.it.LoanManagement;
import uz.learn.it.dto.response.UserCredentialResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.enums.Role;
import uz.learn.it.service.impl.UserServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LoanManagement.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserServiceImpl userService;

    private UserCredentialResponseDTO user;

    @BeforeEach
    void setUp() {
        Client client = Client.builder()
                .id(1L)
                .firstName("Shohruh")
                .lastName("Alimov")
                .passportInfo("AC1525032")
                .phoneNumber("+998908991199")
                .role(Role.ROLE_MANAGER).build();

        user = UserCredentialResponseDTO.builder()
                .id(1)
                .username("+998908991199")
                .password("Shohruh166")
                .build();
    }

    @Test
    @WithMockUser(roles = {"MANAGER"})
    public void shouldReturnAllUserCredentials() throws Exception {
        when(userService.getUserCredentials(any(Integer.class), any(Integer.class)))
                .thenReturn(List.of(user));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].password").value("Shohruh166"))
                .andExpect(jsonPath("$.data[0].username").value("+998908991199"));
    }
}
