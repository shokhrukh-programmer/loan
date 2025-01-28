package uz.learn.it.dto.request;

import lombok.Data;
import uz.learn.it.enums.Role;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private Role role;
}
