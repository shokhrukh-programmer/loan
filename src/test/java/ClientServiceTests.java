import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uz.learn.it.dto.response.ClientRegistrationResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.User;
import uz.learn.it.enums.Role;
import uz.learn.it.repository.ClientDAO;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.impl.ClientServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {
    @Mock
    private ClientDAO clientDAO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
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
    void testAllClients() {
        List<Client> clients = List.of(client);

        when(clientDAO.findAll()).thenReturn(clients);

        // When
        List<Client> result = clientDAO.findAll();

        // Then
        assertEquals(1, result.size());

        assertEquals("Shohruh", result.get(0).getFirstName());
    }

    @Test
    void testUserByClientId() {
        User user = new User(1, "+998908991199", "12345678", Role.ROLE_MANAGER, client);
        when(userDAO.getUserByClientId(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userDAO.getUserByClientId(1L);

        assertTrue(result.isPresent());

        assertEquals("12345678", result.get().getPassword());
    }

    @Test
    void testResetPassword() {
        User user = new User(1, "+998908991199", "12345678", Role.ROLE_MANAGER, client);

        when(userDAO.getUserByClientId(1L)).thenReturn(Optional.of(user));

        ClientRegistrationResponseDTO result = clientService.resetPassword(1L);

        ClientRegistrationResponseDTO c = new ClientRegistrationResponseDTO(client.getPhoneNumber(), "16092505");

        assertNotEquals(c, result);

        assertEquals("+998908991199", result.getUsername());
    }
}
