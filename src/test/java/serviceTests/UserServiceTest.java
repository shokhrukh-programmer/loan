package serviceTests;

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
import uz.learn.it.dto.response.UserCredentialResponseDTO;
import uz.learn.it.entity.Client;
import uz.learn.it.entity.User;
import uz.learn.it.enums.Role;
import uz.learn.it.repository.UserDAO;
import uz.learn.it.service.impl.UserServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    private Client client;

    private User user;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .id(1L)
                .firstName("Shohruh")
                .lastName("Alimov")
                .passportInfo("AC1525032")
                .phoneNumber("+998908991199")
                .role(Role.ROLE_MANAGER).build();

        user = User.builder()
                .id(1)
                .username("+998908991199")
                .password("Shohruh2002")
                .role(Role.ROLE_USER)
                .client(client).build();
    }

    @Test
    public void shouldReturnUserCredentials() {
        Page<User> userPage = new PageImpl<>(List.of(user));

        when(userDAO.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(userPage);

        List<UserCredentialResponseDTO> result = userService.getUserCredentials(0, 10);

        assertEquals(client.getId(), result.get(0).getClientId());
        assertEquals("+998908991199", result.get(0).getUsername());
        assertEquals("Shohruh2002", result.get(0).getPassword());
    }
}
